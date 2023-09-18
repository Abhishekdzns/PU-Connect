package com.example.puconnect.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill(
    var skillName:String="",
    var percentage:String=""
):Parcelable