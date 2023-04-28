package com.donatienthorez.chatgptbot.chat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import androidx.activity.compose.setContent
import com.donatienthorez.chatgptbot.ui.ChatGptBotAppTheme

class ChatActivity : ComponentActivity() {

    private val viewModel: ChatViewModel by stateViewModel(
        state = { intent?.extras ?: Bundle() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatGptBotAppTheme {
                ChatScreen(
                    uiHandlers = ChatScreenUiHandlers(
                        onSendMessage = viewModel::sendMessage,
                        onResendMessage = viewModel::resendMessage
                    ),
                    messageList = viewModel.conversation
                )
            }
        }
    }
}