package com.example.puconnect.domain.use_cases.AuthenticationUseCases

import com.example.puconnect.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignUp @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(email: String, password: String, userName: String) =
        repository.firebaseSignUp(email, password, userName)
}