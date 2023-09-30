package com.example.puconnect.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.FieldValue
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Post(
    var postId: String = "",
    var postTitle: String = "",
    var postDesc: String = "",
    var postType: String = "",
    var postUser: User = User(),
    var engageCount: Int = 0,
    var timestamp: Long = System.currentTimeMillis()
) : Parcelable