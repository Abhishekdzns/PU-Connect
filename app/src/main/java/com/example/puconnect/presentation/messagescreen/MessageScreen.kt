package com.example.puconnect.presentation.messagescreen

import android.hardware.ConsumerIrManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.puconnect.R
import com.example.puconnect.domain.model.User
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.MessageViewModel
import com.example.puconnect.presentation.chatscreen.common.BottomChatBox
import com.example.puconnect.presentation.chatscreen.common.MessageBox
import com.example.puconnect.presentation.chatscreen.common.UserSection
import com.example.puconnect.presentation.homescreen.components.HorizontalSpacer
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.ui.theme.textFieldBorder
import com.example.puconnect.util.Response

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun MessageScreen(
    navController: NavHostController,
    userDetails: User
) {
    val messageViewModel: MessageViewModel = hiltViewModel()
//    LaunchedEffect(key1 = Unit) {
//        messageViewModel.sendMessage(
//            receiverId = "IcUDiDHMLXYUe1Hr9DFS22d917E2",
//            message = Message(
//                messageText = "Hey what are you doing?",
//                timeStamp = System.currentTimeMillis(),
//                isSentByMe = true,
//                from = "Purab Modi",
//                to = "squeesy",
//            )
//        )
//
//    }
    messageViewModel.getAllMessages(userDetails.userId)

    when (val response = messageViewModel.sendMessage.value) {
        is Response.Error -> {
            Toast(message = "Message cant be sent due to Unexpected Error")
        }

        Response.Loading -> {
        }

        is Response.Success -> {
        }
    }

    when (val response = messageViewModel.messages.value) {
        is Response.Error -> {
            Toast(message = "Unexpected Error")
        }

        Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            val msgList = response.data
            Scaffold(
                containerColor = Color.White,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                fontFamily = gilroy,
                                text = userDetails.name,
                                fontWeight = FontWeight.W600,
                                fontSize = 16.sp,
                                lineHeight = 19.6.sp,
                                color = Color.White
                            )
                        },
                        navigationIcon = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    modifier = Modifier
                                        .padding(start = 17.dp)
                                        .clickable { navController.popBackStack() },
                                    imageVector = ImageVector.vectorResource(id = R.drawable.arrowleft),
                                    contentDescription = null,
                                    tint = Color.White
                                )


                                HorizontalSpacer(width = 16)
                                val userImagePainter = rememberImagePainter(
                                    data = userDetails.imageUrl,
                                    builder = {
                                        // You can apply transformations here if needed
                                        transformations(CircleCropTransformation())
                                    }
                                )
                                Image(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .clip(CircleShape),
                                    painter = userImagePainter,
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black)
                    )
                },
                bottomBar = {
                    BottomChatBox2(userDetails.name, onSend = { message ->
                        messageViewModel.sendMessage(
                            userDetails.userId,
                            message
                        )
                    })
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth()
                        .fillMaxSize(),

                    ) {

                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(1.dp))
                        }

                        items(msgList) { chatMsg ->
                            MessageDesign(chatMsg)
                        }
                    }
                }
            }
        }
    }


}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun MessageScreenPreview() {
    // MessageScreen(userName = "Jeet Shah", userPhoto = R.drawable.userphoto3)
}