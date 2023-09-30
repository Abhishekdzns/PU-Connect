package com.example.puconnect.presentation.networkscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.puconnect.mockdata.network.netUserList
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.NetworkViewModel
import com.example.puconnect.presentation.common.CustomTopAppBar
import com.example.puconnect.presentation.navigation.Destinations
import com.example.puconnect.presentation.networkscreen.common.NetworkSearchSection
import com.example.puconnect.presentation.networkscreen.common.NetworkUserInfo
import com.example.puconnect.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkScreen(
    navController: NavHostController,
    padding: PaddingValues
) {
    val networkViewModel: NetworkViewModel = hiltViewModel()
    networkViewModel.getAllUsers()
    when (val response = networkViewModel.users.value) {
        is Response.Error -> {
            Toast(message = "Unexpected Error")
        }

        Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            val usersList = response.data
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 20.dp),
            ) {
                CustomTopAppBar(
                    title = "Network",
                    onShareClick = { navController.navigate(Destinations.NewMessageScreen.route) },
                    onBellClick = { navController.navigate(Destinations.NotificationScreen.route) }
                )

                NetworkSearchSection()

                LazyColumn() {
                    items(usersList) { networkUserData ->
                        NetworkUserInfo(networkUserData = networkUserData, navController = navController)
                    }
                }
            }
        }
    }




}


@Preview(showSystemUi = true)
@Composable
fun NetworkScreenPreview() {
    //NetworkScreen()
}