package com.example.puconnect.domain.use_cases.PostUseCases

import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.repository.PostRepository
import javax.inject.Inject

class UploadPost @Inject constructor(
    private val repository: PostRepository,
) {
    operator fun invoke(post: Post, userId: String) = repository.uploadPost(post, userId)
}