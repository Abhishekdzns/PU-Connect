package com.example.puconnect.domain.use_cases.AuthenticationUseCases

import com.example.puconnect.domain.repository.AuthenticationRepository
import javax.inject.Inject

class FirebaseSignOut @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.firebaseSignOut()
}