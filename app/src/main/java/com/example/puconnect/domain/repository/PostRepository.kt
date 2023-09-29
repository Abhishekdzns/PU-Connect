package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.Chat
import com.example.puconnect.domain.model.Post
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getAllPosts(): Flow<Response<List<Post>>>

    fun getPostByGuild(guildName: String): Flow<Response<List<Post>>>

    fun getMessagesByPost(postId: String): Flow<Response<List<Chat>>>
    fun uploadPost(post: Post, userId: String?): Flow<Response<Boolean>>

    fun uploadMessage(chat: Chat, postId: String, userId: String?): Flow<Response<Boolean>>

}