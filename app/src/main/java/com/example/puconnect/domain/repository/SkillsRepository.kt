package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface SkillsRepository {

    fun getSkills(): Flow<Response<List<GenreWithSkills>>>

    fun setSkills(skillsList:List<GenreWithSkills>):Flow<Response<Boolean>>

}