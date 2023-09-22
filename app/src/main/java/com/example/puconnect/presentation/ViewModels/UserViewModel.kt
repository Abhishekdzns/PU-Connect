package com.example.puconnect.presentation.ViewModels

import android.net.Uri
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

    private val _updateUserData = mutableStateOf<Response<Boolean>>(Response.Loading)
    val updateUserData: State<Response<Boolean>> = _updateUserData

    private val _setSkills = mutableStateOf<Response<Boolean>>(Response.Loading)
    val setSkills: State<Response<Boolean>> = _setSkills

    private val _getUserDataOnce = mutableStateOf<Response<User?>>(Response.Success(null))
    val getUserDataOnce: State<Response<User?>> = _getUserDataOnce

    private val _uploadImage = mutableStateOf<Response<Boolean>>(Response.Loading)
    val uploadImage: State<Response<Boolean>> = _uploadImage

    private val _getImage = mutableStateOf<Response<String>>(Response.Loading)
    val getImage: State<Response<String>> = _getImage

    fun getUserInfo() {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.getUserDetails(userId).collect() {
                    _getUserData.value = it
                }
            }
        }
    }

    fun getUserInfoOnce() {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.getUserDetailsOnce(userId).collect() {
                    _getUserDataOnce.value = it
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

    fun updateUserInfo(
        user: User
    ) {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.updateUserData(userId, user).collect {
                    _updateUserData.value = it
                }
            }
        }
    }

    fun setSkills(
        skillsList: List<Skill>
    ) {
        if (userId != null) {
            viewModelScope.launch {
                userUseCases.setSkills(userId, skillsList)
                    .collect {
                        _setSkills.value = it
                    }
            }
        }
    }

    fun uploadProfileImage(imageUri: ByteArray) {
        viewModelScope.launch {
            userUseCases.uploadImage(userId!!, imageUri).collect {
                _uploadImage.value = it
            }
        }
    }

    fun getProfileImageUrl() {
        viewModelScope.launch {
            userUseCases.getImage(userId!!).collect {
                _getImage.value = it
            }
        }

    }

}