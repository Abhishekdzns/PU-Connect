package com.example.puconnect.domain.use_cases.PostUseCases

data class PostUseCases(
    val getPosts: GetPosts,
    val uploadPost: UploadPost,
    val getPostByGuild: GetPostByGuild,
    val getMessagesByPost: GetMessagesByPost,
    val uploadMessage: UploadMessage
)