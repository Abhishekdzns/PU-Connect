package com.example.puconnect.domain.use_cases.SkillsUseCases

import com.example.puconnect.data.SkillsRepositoryImpl
import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.domain.repository.SkillsRepository

class SetSkills(
    private val repository: SkillsRepository
) {
    operator fun invoke(skillsList:List<GenreWithSkills>) = repository.setSkills(skillsList)
}