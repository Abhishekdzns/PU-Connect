package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.User
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    fun getAllUsers(userId: String?): Flow<Response<List<User>>>

}