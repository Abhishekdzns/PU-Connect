package com.example.puconnect.data

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
    override fun getSkills(): Flow<Response<List<GenreWithSkills>>> = callbackFlow {
        Response.Loading
        val snapShotListener =
            firebaseFirestore.collection(Constants.COLLECTION_NAME_SKILLS).get().await()
        val skillsList = mutableListOf<GenreWithSkills>()

        for (document in snapShotListener.documents) {
            val data = document.toObject(GenreWithSkills::class.java)
            data?.let { skillsList.add(it) }
        }

        Response.Success(skillsList)

    }
}