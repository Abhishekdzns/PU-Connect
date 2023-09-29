package com.example.puconnect.domain.use_cases.PostUseCases

import com.example.puconnect.domain.model.Chat
import com.example.puconnect.domain.repository.PostRepository
import javax.inject.Inject

class UploadMessage @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(chat: Chat, postId: String, userId: String) =
        repository.uploadMessage(chat, postId, userId)
}