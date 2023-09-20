package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.UserRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : UserRepository {
    private var operationSuccessful: Boolean = false
    override fun getUserDetails(userId: String): Flow<Response<User>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
            .document(userId)
            .addSnapshotListener { value, error ->
                val response = if (value != null) {
                    val userInfo = value.toObject(User::class.java)
                    Response.Success(userInfo!!)
                } else {
                    Response.Error(error?.message ?: error.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose {
            snapShotListener.remove()
        }
    }

    override fun getUserDetailsOnce(userId: String): Flow<Response<User>> = flow {
        emit(Response.Loading)
        try {
            val snapshot = firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS)
                .document(userId)
                .get()
                .await()
            val data = snapshot.toObject(User::class.java)
            if (data != null) {
                emit(Response.Success(data))
            } else {
                emit(Response.Error("Unexpected Error"))
            }
        } catch (e: Exception) {
            Log.d("ERRORINAWIT", "getUserDetailsOnce: ${e.message} ${e.cause}")
            e.printStackTrace()
            emit(Response.Error("Unexpected Error"))
        }
    }

    override fun setUserDetails(
        userId: String,
        name: String,
        userName: String,
        role: String
    ): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            val userObj = mutableMapOf<String, String>()
            userObj["name"] = name
            userObj["userName"] = userName
            userObj["role"] = role
            firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS).document(userId)
                .update(userObj as Map<String, Any>)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Not Updated"))
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }
    }

    override fun updateUserDetails(userId: String, user: User): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS).document(userId)
                .set(user)
                .addOnSuccessListener {
                    operationSuccessful = true
                }.await()
            if (operationSuccessful) {
                emit(Response.Success(operationSuccessful))
            } else {
                emit(Response.Error("Not Updated"))
            }
        } catch (e: Exception) {
            Response.Error(e.localizedMessage ?: "An Unexpected Error")
        }
    }

    override fun setSkills(userId: String, skillsList: List<Skill>): Flow<Response<Boolean>> =
        flow {
            operationSuccessful = false
            try {
                val userObj = mutableMapOf<String, List<Skill>>()
                userObj["skills"] = skillsList
                firebaseFirestore.collection(Constants.COLLECTION_NAME_USERS).document(userId)
                    .update(userObj as Map<String, Any>)
                    .addOnSuccessListener {
                        operationSuccessful = true
                    }.await()
                if (operationSuccessful) {
                    emit(Response.Success(operationSuccessful))
                } else {
                    emit(Response.Error("Not Updated"))
                }
            } catch (e: Exception) {
                Response.Error(e.localizedMessage ?: "An Unexpected Error")
            }
        }


}