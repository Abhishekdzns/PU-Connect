package com.example.puconnect.presentation.profilescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.puconnect.R
import com.example.puconnect.domain.model.User
import com.example.puconnect.presentation.Toast
import com.example.puconnect.presentation.ViewModels.SkillsViewModel
import com.example.puconnect.presentation.ViewModels.UserViewModel
import com.example.puconnect.presentation.common.VerticalSpacer
import com.example.puconnect.presentation.homescreen.components.HorizontalSpacer
import com.example.puconnect.presentation.navigation.Destinations
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.ui.theme.textFieldBorder
import com.example.puconnect.util.Response
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
    navController: NavHostController,
) {
    val userViewModel: UserViewModel = hiltViewModel()
    userViewModel.getUserInfo()
    when (val response = userViewModel.getUserData.value) {
        is Response.Error -> {
            Toast(message = "Unexpected Error")
        }

        Response.Loading -> {
            CircularProgressIndicator()
        }

        is Response.Success -> {
            if (response.data != null) {
                val obj = response.data
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp)
                        .padding(paddingValues = paddingValues)


                ) {

                    VerticalSpacer(height = 19)

                    ProfileSection(
                        onEditProfileClick = { navController.navigate(Destinations.EditProfileScreen.route) },
                        obj
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "mySkillsList",
                        value = obj.skills
                    )
                    ProfileScreenSkillSection(
                        onEditClick = { navController.navigate(Destinations.EditSkillsScreen.route) },
                        onEdit2Click = { navController.navigate(Destinations.EditProfileScreen.route) },
                        obj
                    )
                }
            }
        }
    }

}


@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileSection(
    onEditProfileClick: () -> Unit,
    obj: User
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .height(207.dp)

    ) {
        Text(
            modifier = Modifier
                .padding(top = 9.dp)
                .align(Alignment.TopStart),
            text = "Profile",
            fontWeight = FontWeight.W600,
            fontFamily = gilroy,
            fontSize = 20.sp,
            lineHeight = 24.5.sp,
            color = Color.Black
        )

        Box(
            modifier = Modifier
                .height(32.dp)
                .width(90.dp)
                .background(shape = RoundedCornerShape(32.dp), color = Color.Black)
                .align(Alignment.TopEnd)
                .clickable { onEditProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Edit Profile",
                fontFamily = gilroy,
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 14.56.sp,
                color = Color.White
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .border(
                    shape = RoundedCornerShape(4.dp),
                    width = (0.25).dp,
                    color = textFieldBorder
                )
                .padding(16.dp)
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                val profileImagePainter = rememberImagePainter(
                    data = obj.imageUrl,
                    builder = {
                        // You can apply transformations here if needed
                        transformations(CircleCropTransformation())
                    }
                )
                Image(
                    modifier = Modifier.size(108.dp),
                    painter = profileImagePainter,
                    contentDescription = null
                )

                HorizontalSpacer(width = 24)

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = obj.name,
                        fontFamily = gilroy,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600,
                        lineHeight = 22.05.sp,
                        color = Color.Black
                    )

                    VerticalSpacer(height = 12)

                    Text(
                        text = obj.role,
                        fontFamily = gilroy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 14.56.sp,
                        color = textFieldBorder
                    )

                }

            }

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.unkownlogo),
                contentDescription = null,
                tint = Color.Black
            )

        }
    }
}

@Composable
fun ProfileScreenSkillSection(
    onEditClick: () -> Unit,
    onEdit2Click: () -> Unit,
    obj: User,
) {
    LazyColumn() {
        item {
            VerticalSpacer(height = 8)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Skills",
                    fontFamily = gilroy,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W600,
                    lineHeight = 19.6.sp,
                    color = Color.Black
                )

                Row(
                    modifier = Modifier
                        .width(77.dp)
                        .height(32.dp)
                        .border(
                            shape = RoundedCornerShape(32.dp),
                            color = textFieldBorder,
                            width = (0.25).dp
                        )
                        .clickable { onEditClick() },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Edit",
                        fontFamily = gilroy,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        lineHeight = 14.56.sp,
                        color = Color.Black
                    )

                    HorizontalSpacer(width = 8)

                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.pencilsimple),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

            }
        }

    }
    VerticalSpacer(height = 8)

    SkillCircles(onEdit2Click = { onEdit2Click }, obj = obj)
}

@Composable
fun SkillCircles(
    onEdit2Click: () -> Unit,
    obj: User
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {

        item {
            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 4)
            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween,
            ) {
                obj.skills.forEach { skill ->
                    MyCircle(
                        percentage = skill.percentage.toInt(),
                        skillName = skill.skillName,
                        itemSize = itemSize
                    )
                }
            }
        }

        item {
            VerticalSpacer(height = 24)

            AboutMeSection(onEdit2Click = { onEdit2Click() }, obj)
        }
    }

}

@Composable
fun AboutMeSection(
    onEdit2Click: () -> Unit,
    obj: User
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "About me",
                fontFamily = gilroy,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 19.6.sp,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .width(77.dp)
                    .height(32.dp)
                    .border(
                        shape = RoundedCornerShape(32.dp),
                        color = textFieldBorder,
                        width = (0.25).dp
                    )
                    .clickable { onEdit2Click() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Edit",
                    fontFamily = gilroy,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 14.56.sp,
                    color = Color.Black
                )

                HorizontalSpacer(width = 8)

                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.pencilsimple),
                    contentDescription = null,
                    tint = Color.Black
                )
            }

        }
        // row end

        VerticalSpacer(height = 24)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    shape = RoundedCornerShape(4.dp),
                    color = textFieldBorder,
                    width = (0.25).dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            HorizontalSpacer(width = 16)

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.mappin),
                contentDescription = null,
                tint = Color.Black
            )

            HorizontalSpacer(width = 16)

            Text(
                text = "Located in ${obj.city}",
                fontFamily = gilroy,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.98.sp,
                color = Color.Black
            )
        }

        VerticalSpacer(height = 16)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .border(
                    shape = RoundedCornerShape(4.dp),
                    color = textFieldBorder,
                    width = (0.25).dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            HorizontalSpacer(width = 16)

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.graduationcap),
                contentDescription = null,
                tint = Color.Black
            )

            HorizontalSpacer(width = 16)

            Text(
                text = "Studies at ${obj.college}",
                fontFamily = gilroy,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.98.sp,
                color = Color.Black
            )
        }

        VerticalSpacer(height = 24)


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Links",
                fontFamily = gilroy,
                fontSize = 16.sp,
                fontWeight = FontWeight.W600,
                lineHeight = 19.6.sp,
                color = Color.Black
            )

            Row(
                modifier = Modifier
                    .width(77.dp)
                    .height(32.dp)
                    .border(
                        shape = RoundedCornerShape(32.dp),
                        color = textFieldBorder,
                        width = (0.25).dp
                    )
                    .clickable { onEdit2Click() },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Edit",
                    fontFamily = gilroy,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W500,
                    lineHeight = 14.56.sp,
                    color = Color.Black
                )

                HorizontalSpacer(width = 8)

                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.pencilsimple),
                    contentDescription = null,
                    tint = Color.Black
                )
            }

        }

        //Links row end

        VerticalSpacer(height = 16)


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.bluelogo),
                contentDescription = null,
            )

            HorizontalSpacer(width = 16)

            Text(
                text = obj.extraUrl,
                fontFamily = gilroy,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.98.sp,
                color = Color.Black
            )

        }

        VerticalSpacer(height = 43)

        Text(
            text = "My Account",
            fontFamily = gilroy,
            fontSize = 16.sp,
            fontWeight = FontWeight.W600,
            lineHeight = 19.6.sp,
            color = Color.Black
        )

        VerticalSpacer(height = 32)


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.power),
                contentDescription = null,
                tint = Color.Black
            )

            HorizontalSpacer(width = 16)

            Text(
                text = "Deactivate Account",
                fontFamily = gilroy,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.98.sp,
                color = Color.Black
            )

        }

        VerticalSpacer(height = 16)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                modifier = Modifier.size(20.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.signout),
                contentDescription = null,
                tint = Color.Black
            )

            HorizontalSpacer(width = 16)

            Text(
                text = "Logout",
                fontFamily = gilroy,
                fontSize = 14.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 16.98.sp,
                color = Color.Black
            )

        }

        VerticalSpacer(height = 39)


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Version S.A.M 1.0.0",
                fontFamily = gilroy,
                fontSize = 10.sp,
                fontWeight = FontWeight.W500,
                lineHeight = 12.13.sp,
                color = Color.Black
            )

        }

        VerticalSpacer(height = 21)
    }

}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    //ProfileScreen()

}