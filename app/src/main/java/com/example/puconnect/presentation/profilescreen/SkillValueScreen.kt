package com.example.puconnect.presentation.profilescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.puconnect.R
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.mockdata.profile.profileData
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.UserViewModel
import com.example.puconnect.presentation.common.NumericTextField
import com.example.puconnect.presentation.common.VerticalSpacer
import com.example.puconnect.presentation.navigation.BottomBarScreen
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillValueScreen(
    navController: NavHostController,
    mySkillsList: List<Skill>
) {
    val userViewModel: UserViewModel = hiltViewModel()

    when (val response = userViewModel.setSkills.value) {
        is Response.Loading -> {

        }

        is Response.Error -> {
            Toast(message = "An Unexpected Error")
        }

        is Response.Success -> {
            val isUploaded = response.data
            if (isUploaded) {
                Toast(message = "Skills Uploaded")
                navController.navigate(BottomBarScreen.ProfileScreen.route) {
                    popUpTo(BottomBarScreen.ProfileScreen.route) {
                        inclusive = true
                    }
                }
            } else {
                Toast(message = "Error")
            }
        }
    }

    var mySkills by remember {
        mutableStateOf(
            listOf<Skill>(
            )
        )
    }

    LaunchedEffect(key1 = Unit) {
        mySkills = mySkillsList
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
            title = {
                Text(
                    fontFamily = gilroy,
                    text = "Add Skill Proficiency",
                    fontWeight = FontWeight.W600,
                    fontSize = 16.sp,
                    lineHeight = 19.6.sp,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(
                    modifier = Modifier.padding(start = 2.dp),
                    onClick = {
                        navController.popBackStack()
                    }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrowleft),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            },
            actions = {
                Row(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .width(60.dp)
                        .height(32.dp)
                        .background(
                            shape = RoundedCornerShape(32.dp),
                            color = Color.White,
                        )
                        .clickable {
                            userViewModel.setSkills(mySkills)
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Save",
                        fontFamily = gilroy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 14.56.sp,
                        color = Color.Black
                    )

                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black)
        )

        VerticalSpacer(height = 19)

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {

            itemsIndexed(mySkills) { index, skill ->
                SkillItem(skill = skill,
                    onPercentageChanged = { newPercentage ->
                        mySkills = mySkills.toMutableList().apply {
                            this[index].percentage = newPercentage.toString()
                        }
                    })
            }


        }
    }
}

@Composable
fun SkillItem(skill: Skill, onPercentageChanged: (Int) -> Unit) {
    var editPercentage by remember {
        mutableStateOf(skill.percentage)
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = skill.skillName,
            fontFamily = gilroy,
            fontSize = 14.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 17.15.sp,
            color = Color.Black
        )

        VerticalSpacer(height = 8)

        NumericTextField(placeholder = profileData.fullName,
            value = editPercentage,
            onValueChange = { newValue ->
                val intValue = newValue.toIntOrNull()

                if (intValue != null) {
                    // Enforce the range from 10 to 100
                    editPercentage = if (intValue > 100) {
                        "100"
                    } else {
                        intValue.toString()
                    }
                    onPercentageChanged(editPercentage.toInt())
                } else if (newValue.isEmpty()) {
                    editPercentage = ""
                }

            }
        )

        VerticalSpacer(height = 10)
    }
}