package com.example.puconnect.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Chat(
    var chatId: String = "",
    var userName: String = "",
    var postDesc: String = ""
) : Parcelable
