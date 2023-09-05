package com.example.puconnect.domain.use_cases.AuthenticationUseCases

data class AuthenticationUserCases (
    val isUserAuthenticated: isUserAuthenticated,
    val firebaseSignIn: FirebaseSignIn,
    val firebaseAuthState: FirebaseAuthState,
    val firebaseSignUp: FirebaseSignUp,
    val firebaseSignOut: FirebaseSignOut
)