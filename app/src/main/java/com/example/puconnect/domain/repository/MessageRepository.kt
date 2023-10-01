package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.Conversations
import com.example.puconnect.domain.model.Message
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    fun getAllConversations(userId: String): Flow<Response<List<Conversations>>>

    fun getMessagesByUserId(userId: String, receiverId: String): Flow<Response<List<Message>>>

    fun sendMessageByUserId(
        userId: String,
        receiverId: String,
        message: Message
    ): Flow<Response<Boolean>>

}