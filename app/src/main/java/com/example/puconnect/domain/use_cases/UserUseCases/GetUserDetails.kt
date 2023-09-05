package com.example.puconnect.domain.use_cases.UserUseCases

import com.example.puconnect.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserDetails @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String) = repository.getUserDetails(userId)
}