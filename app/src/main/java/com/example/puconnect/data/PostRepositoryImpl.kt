package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.PostRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

class PostRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : PostRepository {
    var isOperationSuccess = false
    override fun getAllPosts(): Flow<Response<List<Post>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_POSTS)
            .addSnapshotListener { snapShot, e ->
                val response = if (snapShot != null) {
                    val postList = snapShot.toObjects(Post::class.java)
                    Response.Success<List<Post>>(postList)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun uploadPost(post: Post, userId: String?): Flow<Response<Boolean>> = flow {
        isOperationSuccess = false

        var user = User()
        try {

            firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
                .document(userId!!)
                .get()
                .addOnCompleteListener {
                    user = it.result.toObject(User::class.java)!!
                }.await()

            Log.d("UPLOADPOST", "uploadPost: User Details: ${user.name}")
            post.postUser = user

            firebaseFirestore.collection(Constants.COLLECTION_NAME_POSTS)
                .add(post)
                .addOnSuccessListener {
                    isOperationSuccess = true
                }.await()
            if (isOperationSuccess) {
                emit(Response.Success(isOperationSuccess))
            } else {
                emit(Response.Error("Not Updated"))
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }
    }
}