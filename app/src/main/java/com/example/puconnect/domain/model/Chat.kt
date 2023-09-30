package com.example.puconnect.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.FieldValue
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Chat(
    var chatId: String = "",
    var userName: String = "",
    var postDesc: String = "",
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable
