package com.example.puconnect.domain.use_cases.UserUseCases

import com.example.puconnect.domain.repository.UserRepository
import javax.inject.Inject

class SetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(
        userId: String,
        name: String,
        userName: String,
        role: String
    ) = repository.setUserDetails(userId, name, userName, role)
}