package com.example.puconnect.presentation.chatscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.puconnect.R
import com.example.puconnect.domain.model.Post
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.mockdata.chat.msgList
import com.example.puconnect.mockdata.home.UserQueData
import com.example.puconnect.mockdata.home.siddhiQue
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.PostViewModel
import com.example.puconnect.presentation.chatscreen.common.BottomChatBox
import com.example.puconnect.presentation.chatscreen.common.MessageBox
import com.example.puconnect.presentation.chatscreen.common.UserSection
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.ui.theme.textFieldBorder
import com.example.puconnect.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    queData: Post,
    navController: NavHostController
) {

    val postViewModel: PostViewModel = hiltViewModel()
    if (queData.postId.isNotBlank()) {
        postViewModel.getMessagesByPost(queData.postId)
    }

    var postDetails by remember {
        mutableStateOf(
            Post()
        )
    }

    LaunchedEffect(key1 = Unit) {
        postDetails = queData
    }
    when (val response = postViewModel.chatsByPost.value) {
        is Response.Error -> {
            Toast(message = "Unexpected Error")
        }

        Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            val chatList = response.data
            Scaffold(
                containerColor = Color.White,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = postDetails.postType,
                                fontFamily = gilroy,
                                fontWeight = FontWeight.W600,
                                fontSize = 16.sp,
                                lineHeight = 19.6.sp,
                                color = Color.White
                            )
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black),
                        navigationIcon = {
                            IconButton(onClick = {
                                navController.popBackStack()
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.arrowleft),
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomChatBox(postDetails.postId)
                }
            ) {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxWidth()
                        .fillMaxSize(),

                    ) {
                    UserSection(userQueData = queData, modifier = Modifier.padding(start = 20.dp))

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = (0.5).dp,
                        color = textFieldBorder
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(1.dp))
                        }

                        items(chatList) { chatMsg ->
                            MessageBox(chatMsg)
                        }
                    }
                }

            }
        }
    }


}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun ChatScreenPreview() {
    // ChatScreen(queData = siddhiQue,)
}