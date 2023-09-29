package com.example.puconnect.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var postId:String="",
    var postTitle: String = "",
    var postDesc: String = "",
    var postType: String = "",
    var postUser: User = User(),
    var engageCount: Int = 0,
) : Parcelable