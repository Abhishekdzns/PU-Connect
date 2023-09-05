package com.example.puconnect.presentation.navigation

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import com.example.puconnect.presentation.Authentication.AuthenticationViewModel
import com.example.puconnect.presentation.welcomescreen.LoginScreen
import com.example.puconnect.presentation.welcomescreen.SignUpScreen
import com.example.puconnect.presentation.welcomescreen.WelcomeScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigationGraph(authenticationViewModel: AuthenticationViewModel) {

    Log.d("ROOT", "In ROOT nav graph")


    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Graphs.AUTH) {



        composable(route = Graphs.MAIN) {
            MainScreen()
        }

        composable(route = Graphs.AUTH) {
            WelcomeScreen(navController = navController, authenticationViewModel = authenticationViewModel)
        }

        composable(route = Graphs.LOGIN) {
            LoginScreen(navController = navController,authenticationViewModel)
        }

        composable(route = Graphs.SIGNIN) {
            SignUpScreen(navController = navController,authenticationViewModel)
        }


    }

}