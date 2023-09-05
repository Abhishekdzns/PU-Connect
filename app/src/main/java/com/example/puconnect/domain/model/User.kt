package com.example.puconnect.domain.model

data class User(
    var name: String = "",
    var userName: String = "",
    var userId: String = "",
    var email: String = "",
    var password: String = "",
    var imageUrl: String = "",
    var role: String = "",
    var skills: List<Skill> = emptyList(),
    var projects: List<String> = emptyList(),
    var extraUrl: String = "",
    var phoneNo: String = "",
    var college: String = "",
    var city: String = "",
    var totalPosts: String = "",
)
