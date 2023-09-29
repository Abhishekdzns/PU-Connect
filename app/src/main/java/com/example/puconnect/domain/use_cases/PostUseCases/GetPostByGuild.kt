package com.example.puconnect.domain.use_cases.PostUseCases

import com.example.puconnect.domain.repository.PostRepository
import javax.inject.Inject

class GetPostByGuild @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(guildName:String) = repository.getPostByGuild(guildName)
}