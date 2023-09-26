package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.Post
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface PostRepository {

    fun getAllPosts() : Flow<Response<List<Post>>>

    fun uploadPost(post: Post, userId: String?):Flow<Response<Boolean>>

}