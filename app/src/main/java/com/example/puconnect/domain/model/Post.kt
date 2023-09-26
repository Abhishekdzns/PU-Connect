package com.example.puconnect.domain.model

data class Post(
    var postTitle:String="",
    var postDesc:String="",
    var postType:String="",
    var postUser:User,
    var engageCount:Int = 0,
    var engageList:List<Chat> = emptyList()
)