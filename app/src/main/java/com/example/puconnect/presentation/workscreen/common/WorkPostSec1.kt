package com.example.puconnect.presentation.workscreen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puconnect.R
import com.example.puconnect.mockdata.work.WorkPostData
import com.example.puconnect.presentation.networkscreen.common.CustomButton2
import com.example.puconnect.ui.theme.addressColor
import com.example.puconnect.ui.theme.gilroy
import com.example.puconnect.ui.theme.textFieldBorder


@Composable
fun WorkPostSec1(
    workPostData: WorkPostData
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                painter = painterResource(id = workPostData.profilePic),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.height(40.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workPostData.organization,
                    fontFamily = gilroy,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    lineHeight = 16.98.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.mappin),
                        contentDescription = null,
                        tint = textFieldBorder
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = workPostData.address,
                        fontFamily = gilroy,
                        fontWeight = FontWeight.W400,
                        fontSize = 12.sp,
                        lineHeight = 14.4.sp,
                        color = addressColor
                    )

                }

            }
        }

        //Spacer(modifier = Modifier.weight(screenWidth*0.174f))

        CustomButton2(onClick = {}, title = "Apply")


    }
}