package com.example.puconnect.util

import androidx.compose.ui.graphics.Color
import com.example.puconnect.ui.theme.msggreen
import com.example.puconnect.ui.theme.msgpurple
import com.example.puconnect.ui.theme.msgred
import kotlin.random.Random

class RandomColor {

    fun getRandomColor(): Color {
        val colors = arrayOf(msgred, msggreen, msgpurple)
        return colors[Random.nextInt(colors.size)]
    }
}