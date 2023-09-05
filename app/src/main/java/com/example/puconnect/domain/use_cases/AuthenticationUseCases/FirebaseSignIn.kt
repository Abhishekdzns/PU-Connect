package com.example.puconnect.domain.use_cases.AuthenticationUseCases

import com.example.puconnect.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignIn @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(email:String,password:String) = repository.firebaseSignIn(email, password)
}