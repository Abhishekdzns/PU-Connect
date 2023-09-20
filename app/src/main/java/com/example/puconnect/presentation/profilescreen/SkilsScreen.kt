package com.example.puconnect.presentation.profilescreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.puconnect.R
import com.example.puconnect.domain.model.GenreWithSkills
import com.example.puconnect.domain.model.Skill
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.SkillsViewModel
import com.example.puconnect.presentation.common.VerticalSpacer
import com.example.puconnect.presentation.navigation.Destinations
import com.example.puconnect.ui.theme.addressColor
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.ui.theme.textFieldBorder
import com.example.puconnect.util.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsScreen(
    navController: NavHostController,
    mySkillsList: List<Skill>
) {
    val skillsViewModel: SkillsViewModel = hiltViewModel()
    skillsViewModel.getSkills()

    var mySkills by remember {
        mutableStateOf(
            listOf<Skill>(
            )
        )
    }

    LaunchedEffect(key1 = Unit) {
        mySkills = mySkillsList
    }

    when (val response = skillsViewModel.skills.value) {
        is Response.Error -> {
            Toast(message = "Unexpected Error")
        }

        Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            val obj = response.data
            Scaffold(
                containerColor = Color.White,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                fontFamily = gilroy,

                                text = "Skills",
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
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black)
                    )

                },
                bottomBar = {
                    Column(
                        modifier = Modifier.navigationBarsPadding()
                    ) {
                        VerticalSpacer(height = 16)

                        Button(
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .padding(horizontal = 20.dp)
                                .background(
                                    shape = RoundedCornerShape(4.dp),
                                    color = Color.Black
                                ),
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "mySkillsList",
                                    value = mySkills
                                )
                                navController.navigate(Destinations.SkillValueScreen.route)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                        ) {

                            Text(
                                fontFamily = gilroy,

                                text = "Save",
                                fontWeight = FontWeight.W600,
                                fontSize = 14.sp,
                                lineHeight = 17.15.sp,
                                color = Color.White
                            )

                        }

                        VerticalSpacer(height = 16)
                    }
                }
            ) { it ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                        .padding(horizontal = 20.dp)
                ) {


                    MySkills()

                    SkillSet(obj, mySkills, addSkill = { skill ->
                        mySkills = mySkills + Skill(skillName = skill, "0")
                    }, removeSkill = { skill ->
                        mySkills = mySkills.filterNot { myskill -> myskill.skillName == skill }
                            .toMutableList()
                    })

                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySkills() {

    var searchText by remember {
        mutableStateOf("")
    }

    Column {

        VerticalSpacer(height = 32)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Skills",
                fontWeight = FontWeight.W600,
                fontFamily = gilroy,
                fontSize = 20.sp,
                lineHeight = 24.sp,
                color = Color.Black
            )

            Text(
                text = "max 6",
                fontWeight = FontWeight.W400,
                fontFamily = gilroy,
                fontSize = 14.sp,
                lineHeight = 16.8.sp,
                color = textFieldBorder
            )
        }

        VerticalSpacer(height = 24)

        Text(
            text = "This helps us recommend you relevant gigs and people to connect with",
            fontWeight = FontWeight.W400,
            fontFamily = gilroy,
            fontSize = 13.sp,
            lineHeight = 15.6.sp,
            color = Color.Black
        )

        VerticalSpacer(height = 32)

        TextField(
            shape = RoundedCornerShape(4.dp),
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .height(44.dp)
                .border(
                    width = (0.25).dp,
                    shape = RoundedCornerShape(4.dp),
                    color = textFieldBorder
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            placeholder = {
                Text(
                    text = "Search Skills like Figma, AWS",
                    fontFamily = gilroy,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 14.56.sp,
                    color = addressColor
                )
            },
            textStyle = TextStyle(
                fontFamily = gilroy,
                fontSize = 12.sp,
                lineHeight = 14.56.sp,
                fontWeight = FontWeight.W500,
                color = addressColor

            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier,
                    imageVector = ImageVector.vectorResource(id = R.drawable.icon_search),
                    contentDescription = "Search Icon"
                )
            },
        )

        VerticalSpacer(height = 24)

    }
}

@Composable
fun SkillChip(
    skill: String,
    isSelected: Boolean,
    addSkill: (skill: String) -> Unit,
    removeSkill: (skill: String) -> Unit
) {
    val chipBackgroundColor = if (isSelected) Color.Black else Color.White
    val chipTextColor = if (isSelected) Color.White else Color.Black
    val chipBorderColor = if (isSelected) Color.White else Color.Black
    Box(
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = {
                    if (isSelected) {
                        removeSkill(skill)
                    } else {
                        addSkill(skill)
                    }
                }
            )
            .height(32.dp)
            .border(shape = RoundedCornerShape(20.dp), color = chipBorderColor, width = (0.25).dp)
            .background(shape = RoundedCornerShape(20.dp), color = chipBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = skill,
            fontFamily = gilroy,
            fontSize = 12.sp,
            fontWeight = FontWeight.W500,
            lineHeight = 14.56.sp,
            color = chipTextColor
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SkillSet(
    allGenres: List<GenreWithSkills>,
    mySkills: List<Skill>,
    addSkill: (skill: String) -> Unit,
    removeSkill: (skill: String) -> Unit
) {

    val mySkillsGenre = GenreWithSkills("My Skills", mySkills)
    val newGenreList = listOf(mySkillsGenre) + allGenres

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        newGenreList.forEach { genreWithSkills ->
            item {
                Column {
                    Text(
                        text = genreWithSkills.genreName,
                        fontFamily = gilroy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W600,
                        lineHeight = 19.6.sp,
                        color = Color.Black
                    )

                    VerticalSpacer(height = 8)
                }
            }

            item {
                FlowRow(
                    maxItemsInEachRow = 2,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    genreWithSkills.skillsList.forEach { skill ->
                        val isSelected = mySkills.any {
                            it.skillName == skill.skillName
                        }

                        Column() {
                            VerticalSpacer(height = 8)
                            SkillChip(skill = skill.skillName, isSelected, addSkill = {
                                addSkill(it)
                            }, removeSkill = {
                                removeSkill(it)
                            })

                            VerticalSpacer(height = 8)
                        }

                    }
                }
            }

            item {
                Column {
                    VerticalSpacer(height = 16)

                    Divider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = (0.5).dp,
                        color = textFieldBorder
                    )

                    VerticalSpacer(height = 16)
                }
            }

        }

    }
}

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun SkillsScreenPreview() {
    //SkillChip(skill = "Android")
    //SkillsScreen()
}