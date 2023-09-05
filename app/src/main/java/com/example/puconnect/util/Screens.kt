package com.example.puconnect.util

sealed class Screens(val route:String) {
    object SplashScreen:Screens("splash_screen")
    object LoginScreen:Screens("login_screen")
    object SignUpScreen:Screens("signup_screen")
    object HomeScreen:Screens("home_screen")
}