package com.example.puconnect.domain.model

data class Conversations (
    var userId:String="",
    var messages:List<Message> = emptyList()
)