package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.Conversations
import com.example.puconnect.domain.model.Event
import com.example.puconnect.domain.model.Message
import com.example.puconnect.domain.use_cases.EventUseCases.EventUseCases
import com.example.puconnect.domain.use_cases.MessageUseCases.MessageUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    auth: FirebaseAuth,
    private val messageUseCases: MessageUseCases
) : ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _sendMessage = mutableStateOf<Response<Boolean>>(Response.Loading)
    val sendMessage: State<Response<Boolean>> = _sendMessage

    fun sendMessage(receiverId: String, message: Message) {
        if (userId != null) {
            viewModelScope.launch {
                messageUseCases.sendMessageByUserId(userId, receiverId, message).collect {
                    _sendMessage.value = it
                }
            }
        }
    }

    private val _conversations = mutableStateOf<Response<List<Conversations>>>(Response.Loading)
    val conversations: State<Response<List<Conversations>>> = _conversations

    fun getAllConversations() {
        if (userId != null) {
            viewModelScope.launch {
                messageUseCases.getAllConversations(userId).collect {
                    _conversations.value = it
                }
            }
        }
    }

    private val _messages = mutableStateOf<Response<List<Message>>>(Response.Loading)
    val messages: State<Response<List<Message>>> = _messages

    fun getAllMessages(receiverId: String) {
        if (userId != null && receiverId.isNotBlank()) {
            viewModelScope.launch {
                messageUseCases.getMessagesByUserId(userId, receiverId).collect {
                    _messages.value = it
                }
            }
        }
    }

}