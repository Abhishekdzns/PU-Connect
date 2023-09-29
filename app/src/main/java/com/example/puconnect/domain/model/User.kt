package com.example.puconnect.domain.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
    var portfolioLink: String = "",
    var phoneNo: String = "",
    var college: String = "",
    var city: String = "",
    var totalPosts: String = "",
) : Parcelable
