package com.example.puconnect.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    var eventId: String = "",
    var eventTitle: String = "",
    var eventDesc: String = "",
    var engageCount: Int = 0,
    var eventDate: String = "",
    var isEnrolled: Boolean = false,
    var eventTime: String = "",
    var eventImageUrl: String = "",
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable