package com.example.puconnect.domain.use_cases.UserUseCases

data class UserUseCases(
    val getUserDetails: GetUserDetails,
    val getUserDetailsOnce: GetUserDetailsOnce,
    val setUserDetails: SetUserDetails,
    val setSkills: SetSkills
)
