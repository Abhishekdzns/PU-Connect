package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.Conversations
import com.example.puconnect.domain.model.Message
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.MessageRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : MessageRepository {
    var isOperationSuccess = false
    override fun getAllConversations(userId: String): Flow<Response<List<Conversations>>> =
        callbackFlow {
            Response.Loading
            val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
                .document(userId)
                .collection(Constants.COLLECTION_NAME_CONVERSATIONS)
                .addSnapshotListener { snapShot, e ->
                    val response = if (snapShot != null) {
                        val conversationsList = snapShot.toObjects(Conversations::class.java)
                        Response.Success<List<Conversations>>(conversationsList)
                    } else {
                        Response.Error(e?.message ?: e.toString())
                    }
                    trySend(response).isSuccess
                }
            awaitClose {
                snapShotListener.remove()
            }
        }

    override fun getMessagesByUserId(
        userId: String,
        receiverId: String
    ): Flow<Response<List<Message>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
            .document(userId)
            .collection(Constants.COLLECTION_NAME_CONVERSATIONS)
            .document(receiverId)
            .collection(Constants.COLLECTION_NAME_MESSAGES)
            .orderBy("timeStamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapShot, e ->
                val response = if (snapShot != null) {
                    val messageList = snapShot.toObjects(Message::class.java)
                    Response.Success<List<Message>>(messageList)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun sendMessageByUserId(
        userId: String,
        receiverId: String,
        message: Message
    ): Flow<Response<Boolean>> = flow {
        isOperationSuccess = false

        try {
            val doc = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
                .document(userId)
                .get()
                .await()
            val from = doc.toObject(User::class.java)?.name

            message.from = from ?: " "

            if (from == null) {
                emit(Response.Error("Not Sent"))
            }

            val senderRef =
                firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS).document(userId)
                    .collection(Constants.COLLECTION_NAME_CONVERSATIONS)
                    .document(receiverId)
                    .collection(Constants.COLLECTION_NAME_MESSAGES)
                    .document()

            val receiverRef =
                firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS).document(receiverId)
                    .collection(Constants.COLLECTION_NAME_CONVERSATIONS)
                    .document(userId)
                    .collection(Constants.COLLECTION_NAME_MESSAGES)
                    .document(senderRef.id)

            message.msgId = senderRef.id

            senderRef.set(message)
                .addOnSuccessListener {
                    isOperationSuccess = true
                }.await()

            message.isSentByMe = false

            receiverRef.set(message)
                .addOnSuccessListener {
                    isOperationSuccess = true
                }.await()

            if (isOperationSuccess) {
                emit(Response.Success(isOperationSuccess))
                Log.d("MessageScreenCheck", "sendMessageByUserId: Success")
            } else {
                emit(Response.Error("Not Sent"))
                Log.d("MessageScreenCheck", "sendMessageByUserId: Failure false condition")
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
            Log.d("MessageScreenCheck", "sendMessageByUserId: Failure - ${e.message}")

        }
    }
}