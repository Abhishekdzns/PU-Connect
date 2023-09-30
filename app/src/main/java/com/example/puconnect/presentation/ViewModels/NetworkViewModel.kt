package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.use_cases.NetworkUseCases.NetworkUseCases
import com.example.puconnect.domain.use_cases.PostUseCases.PostUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val networkUseCases: NetworkUseCases
) : ViewModel() {

    private val userId = auth.currentUser?.uid

    private val _users = mutableStateOf<Response<List<User>>>(Response.Loading)
    val users: State<Response<List<User>>> = _users

    fun getAllUsers() {
        if (userId != null) {
            viewModelScope.launch {
                networkUseCases.getAllUsers(userId).collect {
                    _users.value = it
                }
            }
        }
    }

}