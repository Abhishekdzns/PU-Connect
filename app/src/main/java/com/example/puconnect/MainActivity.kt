package com.example.puconnect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.puconnect.presentation.Authentication.AuthenticationViewModel
import com.example.puconnect.presentation.navigation.RootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // for knowing state of keyboard
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
           // AppNavHost()
           // MainScreen()
           // installSplashScreen()
            val authenticationViewModel : AuthenticationViewModel = hiltViewModel()
            RootNavigationGraph(authenticationViewModel)
            //WelcomeScreen(navController = rememberNavController())
        }
    }
}

