package com.example.puconnect.data

import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.NetworkRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : NetworkRepository {

    override fun getAllUsers(userId: String?): Flow<Response<List<User>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
            .whereNotEqualTo("userId", userId)
            .addSnapshotListener { snapShot, e ->
                val response = if (snapShot != null) {
                    val usersList = snapShot.toObjects(User::class.java)
                    Response.Success<List<User>>(usersList)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }

    }
}