package com.example.puconnect.domain.use_cases.UserUseCases

import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserData @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, user: User) =
        repository.updateUserDetails(userId = userId, user = user)
}