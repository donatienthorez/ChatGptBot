package com.donatienthorez.chatgptbot.chat.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ChatScreen() {
    Column {
        Text(
            text = "Hello World",
            color = Color.Black
        )
    }
}