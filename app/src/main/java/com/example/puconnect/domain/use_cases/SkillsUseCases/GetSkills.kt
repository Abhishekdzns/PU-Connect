package com.example.puconnect.domain.use_cases.SkillsUseCases

import com.example.puconnect.domain.repository.SkillsRepository
import javax.inject.Inject

class GetSkills @Inject constructor(
    private val repository: SkillsRepository
) {
    operator fun invoke() = repository.getSkills()
}