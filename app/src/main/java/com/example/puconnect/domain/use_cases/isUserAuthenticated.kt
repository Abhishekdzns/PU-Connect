package com.example.puconnect.domain.use_cases

import com.example.puconnect.domain.repository.AuthenticationRepository
import javax.inject.Inject

class isUserAuthenticated @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke() = repository.isUserAuthenticatedInFirebase()
}