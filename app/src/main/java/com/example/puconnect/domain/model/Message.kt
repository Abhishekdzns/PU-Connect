package com.example.puconnect.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Message(
    var msgId: String = "",
    var messageText: String = "",
    var timeStamp: Long = System.currentTimeMillis(),
    var isSentByMe: Boolean = false,
    var from: String = "",
    var to: String = ""
) : Parcelable