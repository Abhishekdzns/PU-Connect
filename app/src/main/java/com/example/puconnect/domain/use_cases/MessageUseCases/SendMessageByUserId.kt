package com.example.puconnect.domain.use_cases.MessageUseCases

import com.example.puconnect.domain.model.Message
import com.example.puconnect.domain.repository.MessageRepository
import javax.inject.Inject

class SendMessageByUserId @Inject constructor(
    val repository: MessageRepository
) {
    operator fun invoke(
        userId: String,
        receiverId: String,
        message: Message
    ) = repository.sendMessageByUserId(userId, receiverId, message)
}