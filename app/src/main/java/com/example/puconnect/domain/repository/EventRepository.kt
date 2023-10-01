package com.example.puconnect.domain.repository

import com.example.puconnect.domain.model.Event
import com.example.puconnect.util.Response
import kotlinx.coroutines.flow.Flow

interface EventRepository {

    fun getAllEvents(userId: String): Flow<Response<List<Event>>>

    fun uploadEvent(event: Event): Flow<Response<Boolean>>

}