package com.example.puconnect.domain.use_cases.UserUseCases

import com.example.puconnect.domain.model.Skill
import com.example.puconnect.domain.repository.UserRepository
import javax.inject.Inject

class SetSkills @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(userId: String, skillsList: List<Skill>) =
        repository.setSkills(userId, skillsList)
}