package com.example.puconnect.domain.use_cases.EventUseCases

import com.example.puconnect.domain.repository.EventRepository
import javax.inject.Inject

class GetAllEvents @Inject constructor(
    private val repository: EventRepository
) {
    operator fun invoke(userId: String) = repository.getAllEvents(userId)
}