package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.use_cases.UserUseCases.UserUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val userUseCases: UserUseCases
) : ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _getUserData = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserData: State<Response<User?>> = _getUserData

    private val _setUserData = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setUserData: State<Response<Boolean>> = _setUserData

    private val _setSkills = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val setSkills: State<Response<Boolean>> = _setSkills

    fun getUserInfo() {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.getUserDetails(userId).collect {
                    _getUserData.value = it
                }
            }
        }
    }

    fun setUserInfo(
        name: String,
        userName: String,
        role: String
    ) {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.setUserDetails(userId, name, userName, role).collect {
                    _setUserData.value = it
                }
            }
        }
    }

    fun setSkills(
        skillsList:List<Skill>
    ) {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.setSkills(userId, skillsList).collect{
                    _setSkills.value = it
                }
            }
        }
    }
}