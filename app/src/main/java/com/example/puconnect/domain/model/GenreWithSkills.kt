package com.example.puconnect.domain.model

data class GenreWithSkills(
    var genreName:String="",
    var skillsList:List<Skill> = emptyList()
)
