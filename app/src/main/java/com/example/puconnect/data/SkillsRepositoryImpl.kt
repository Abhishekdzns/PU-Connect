package com.example.puconnect.data

import android.util.Log
import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.SkillsRepository
import com.example.puconnect.util.Constants
import com.example.puconnect.util.Response
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SkillsRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : SkillsRepository {
    var operationSuccessful = false
    override fun getSkills(): Flow<Response<List<GenreWithSkills>>> = callbackFlow {
        Response.Loading
        val snapShotListener = firebaseFirestore.collection(Constants.COLLECTION_NAME_SKILLS)
            .addSnapshotListener{snapShot,e->
                val response = if(snapShot!=null){
                    val skillsList = snapShot.toObjects(GenreWithSkills::class.java)
                    Response.Success<List<GenreWithSkills>>(skillsList)
                }else{
                    Response.Error(e?.message?:e.toString())
                }
                trySend(response).isSuccess
            }
        awaitClose{
            snapShotListener.remove()
        }
    }

    override fun setSkills(skillsList: List<GenreWithSkills>): Flow<Response<Boolean>> = flow {
        operationSuccessful = false
        try {
            val db = firebaseFirestore
            val batch = db.batch()
            for (data in skillsList){
                val documentRef = db.collection(Constants.COLLECTION_NAME_SKILLS).document()
                batch.set(documentRef, data)
            }
            batch.commit()
                .addOnSuccessListener {
                    Log.d("SKILL_LIST", "setSkills: Success!")
                    operationSuccessful = true
                }
            if(operationSuccessful){
                emit(Response.Success(operationSuccessful))
            }else{
                emit(Response.Error("Not updated"))
            }
        }catch (e:Exception){
            Response.Error(e.localizedMessage?:"An Unexpected Error")
        }
    }
}