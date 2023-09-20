package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.Skill
import com.example.puconnect.domain.model.User
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserDetails(userId: String): Flow<Response<User>>
    fun getUserDetailsOnce(userId: String): Flow<Response<User>>

    fun setUserDetails(userId: String,
                       name: String,
                       userName: String,
                       role: String):Flow<Response<Boolean>>

    fun updateUserDetails(userId: String, user: User):Flow<Response<Boolean>>

    fun setSkills(userId: String,skillsList: List<Skill>):Flow<Response<Boolean>>
}