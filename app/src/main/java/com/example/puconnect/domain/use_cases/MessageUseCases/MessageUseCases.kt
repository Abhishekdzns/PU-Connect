package com.example.puconnect.domain.use_cases.MessageUseCases

data class MessageUseCases(
    val getAllConversations: GetAllConversations,
    val getMessagesByUserId: GetMessagesByUserId,
    val sendMessageByUserId: SendMessageByUserId
)