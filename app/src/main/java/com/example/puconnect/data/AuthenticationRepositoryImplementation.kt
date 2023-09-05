package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.AuthenticationRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthenticationRepositoryImplementation @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthenticationRepository {
    var operationSuccessful: Boolean = false

    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser != null
    }

    override fun getFirebaseAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener {
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun firebaseSignIn(email: String, password: String): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            emit(Response.Loading)
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                operationSuccessful = true
            }.await()
            emit(Response.Success(operationSuccessful))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected Error"))
        }
    }

    override fun firebaseSignOut(): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            auth.signOut()
            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected Error"))
        }
    }

    override fun firebaseSignUp(
        email: String,
        password: String,
        userName: String
    ): Flow<Response<Boolean>> = flow{
        operationSuccessful=false
        try {
            emit(Response.Loading)
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            if(authResult.user != null){
                operationSuccessful = true
            }
            if (operationSuccessful){
                val userId = auth.currentUser?.uid!!
                val obj = User(userName = userName, userId = userId, password = password, email = email)
                firestore.collection(Constants.COLLECTION_NAME_USERS).document(userId).set(obj).addOnSuccessListener {
                }
                emit(Response.Success(operationSuccessful))
            }else{
                Response.Success(operationSuccessful)
            }
        }catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "An unexpected Error"))
        }
    }
}