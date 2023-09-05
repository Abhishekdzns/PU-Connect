package com.example.puconnect.presentation.Authentication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.use_cases.AuthenticationUseCases.AuthenticationUserCases
import com.example.puconnect.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val authenticationUserCases: AuthenticationUserCases
) : ViewModel() {

    val isUserAuthenticated get() = authenticationUserCases.isUserAuthenticated()

    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState: State<Response<Boolean>> = _signInState

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState

    private val _signOutState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signOutState: State<Response<Boolean>> = _signOutState

    private val _authState = mutableStateOf<Boolean>(false)
    val authState: State<Boolean> = _authState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authenticationUserCases.firebaseSignIn(email, password).collect {
                _signInState.value = it
            }
        }
    }

    fun signUp(email: String, password: String, userName: String) {
        viewModelScope.launch {
            authenticationUserCases.firebaseSignUp(email, password, userName).collect {
                _signUpState.value = it
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authenticationUserCases.firebaseSignOut().collect {
                _signOutState.value = it
                if (it == Response.Success(true)) {
                    _signInState.value = Response.Success(false)
                }
            }
        }
    }

    fun getAuthState() {
        viewModelScope.launch {
            authenticationUserCases.firebaseAuthState().collect {
                _authState.value = it
            }
        }
    }

}