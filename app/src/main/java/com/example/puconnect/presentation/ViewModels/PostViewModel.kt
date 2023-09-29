package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.Chat
import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.use_cases.PostUseCases.PostUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val postUseCases: PostUseCases
) : ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _posts = mutableStateOf<Response<List<Post>>>(Response.Loading)
    val posts: State<Response<List<Post>>> = _posts

    fun getPosts() {
        viewModelScope.launch {
            postUseCases.getPosts().collect {
                _posts.value = it
            }
        }
    }

    private val _postsByGuild = mutableStateOf<Response<List<Post>>>(Response.Loading)
    val postsByGuild: State<Response<List<Post>>> = _postsByGuild

    fun getPostsByGuild(guildName: String) {
        viewModelScope.launch {
            postUseCases.getPostByGuild(guildName).collect {
                _postsByGuild.value = it
            }
        }
    }

    private val _chatsByPost = mutableStateOf<Response<List<Chat>>>(Response.Loading)
    val chatsByPost: State<Response<List<Chat>>> = _chatsByPost

    fun getMessagesByPost(postId: String) {
        viewModelScope.launch {
            postUseCases.getMessagesByPost(postId).collect {
                _chatsByPost.value = it
            }
        }
    }

    private val _uploadPost = mutableStateOf<Response<Boolean>>(Response.Loading)
    val uploadPost: State<Response<Boolean>> = _uploadPost

    fun uploadPost(post: Post) {
        if (userId != null) {
            viewModelScope.launch {
                postUseCases.uploadPost(post, userId).collect {
                    _uploadPost.value = it
                }
            }
        }
    }

    private val _uploadChat = mutableStateOf<Response<Boolean>>(Response.Loading)
    val uploadChat: State<Response<Boolean>> = _uploadChat

    fun uploadChat(chat: Chat, postId: String) {
        if (userId != null) {
            viewModelScope.launch {
                postUseCases.uploadMessage(chat, postId,userId).collect {
                    _uploadChat.value = it
                }
            }
        }
    }
}