package com.example.puconnect.domain.use_cases.PostUseCases

import com.example.puconnect.domain.repository.PostRepository
import javax.inject.Inject

class GetPosts @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke() = repository.getAllPosts()
}