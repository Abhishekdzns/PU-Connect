package com.example.puconnect.domain.use_cases.NetworkUseCases

import com.example.puconnect.domain.repository.NetworkRepository

class GetAllUsers(
    private val repository: NetworkRepository
) {
    operator fun invoke(userId:String) = repository.getAllUsers(userId)
}