package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.Event
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.EventRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : EventRepository {
    var isOperationSuccess = false
    override fun getAllEvents(userId: String): Flow<Response<List<Event>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_EVENTS)
            .addSnapshotListener { snapShot, e ->
                val response = if (snapShot != null) {
                    val eventList = snapShot.toObjects(Event::class.java)
                    Response.Success<List<Event>>(eventList)
                } else {
                    Response.Error(e?.message ?: e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun uploadEvent(event: Event): Flow<Response<Boolean>> = flow {
        isOperationSuccess = false

        try {

            val docRef = firebaseFirestore.collection(Constants.COLLECTION_NAME_EVENTS).document()
            event.eventId = docRef.id

            docRef.set(event)
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