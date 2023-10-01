package com.example.puconnect.presentation.ViewModels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puconnect.domain.model.Event
import com.example.puconnect.domain.model.User
import com.example.puconnect.domain.use_cases.EventUseCases.EventUseCases
import com.example.puconnect.domain.use_cases.NetworkUseCases.NetworkUseCases
import com.example.puconnect.util.Response
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    auth: FirebaseAuth,
    private val eventUseCases: EventUseCases
) : ViewModel() {
    private val userId = auth.currentUser?.uid

    private val _events = mutableStateOf<Response<List<Event>>>(Response.Loading)
    val events: State<Response<List<Event>>> = _events

    fun getAllEvents() {
        if (userId != null) {
            viewModelScope.launch {
                eventUseCases.getAllEvents(userId).collect {
                    _events.value = it
                }
            }
        }
    }

    private val _uploadEvent = mutableStateOf<Response<Boolean>>(Response.Loading)
    val uploadEvent: State<Response<Boolean>> = _uploadEvent

    fun uploadEvent(event: Event) {
        if (userId != null) {
            viewModelScope.launch {
                eventUseCases.uploadEvent(event).collect {
                    _uploadEvent.value = it
                }
            }
        }
    }

}