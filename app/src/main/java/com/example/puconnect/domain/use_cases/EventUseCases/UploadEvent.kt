package com.example.puconnect.domain.use_cases.EventUseCases

import com.example.puconnect.domain.model.Event
import com.example.puconnect.domain.repository.EventRepository
import javax.inject.Inject

class UploadEvent @Inject constructor(
    val repository: EventRepository
) {
    operator fun invoke(event: Event) = repository.uploadEvent(event)
}