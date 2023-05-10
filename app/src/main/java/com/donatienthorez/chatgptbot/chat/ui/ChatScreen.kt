package com.donatienthorez.chatgptbot.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import com.donatienthorez.chatgptbot.R
import com.donatienthorez.chatgptbot.chat.data.Conversation
import com.donatienthorez.chatgptbot.chat.data.Message
import com.donatienthorez.chatgptbot.chat.data.MessageStatus
import com.donatienthorez.chatgptbot.utils.HorizontalSpacer
import com.donatienthorez.chatgptbot.utils.VerticalSpacer
import kotlinx.coroutines.launch

data class ChatScreenUiHandlers(
    val onSendMessage: (String) -> Unit = {},
    val onResendMessage: (Message) -> Unit = {}
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    uiHandlers: ChatScreenUiHandlers = ChatScreenUiHandlers(),
    conversation: LiveData<Conversation>,
    isSendingMessage: LiveData<Boolean>
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var inputValue by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val conversationState by conversation.observeAsState()
    val isSendingMessageState by isSendingMessage.observeAsState()

    fun sendMessage() {
        uiHandlers.onSendMessage(inputValue)
        inputValue = ""
        coroutineScope.launch {
            listState.animateScrollToItem(conversationState?.list?.size ?: 0)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 16.dp)
                .padding(vertical = 16.dp)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                conversationState?.let {
                    MessageList(
                        messagesList = it.list,
                        listState = listState,
                        onResendMessage = uiHandlers.onResendMessage
                    )
                }
            }
            Row {
                TextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions {
                        sendMessage()
                    },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
                HorizontalSpacer(8.dp)
                Button(
                    modifier = Modifier.height(56.dp),
                    onClick = { sendMessage() },
                    enabled = inputValue.isNotBlank() && isSendingMessageState != true,
                ) {
                    if (isSendingMessageState == true) {
                        Icon(
                            imageVector = Icons.Default.Sync,
                            contentDescription = "Sending"
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send"
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun MessageList(
    messagesList: List<Message>,
    listState: LazyListState,
    onResendMessage: (Message) -> Unit
) {
    LazyColumn(
        state = listState
    ) {
        items(messagesList) { message ->
            Row {
                if (message.isFromUser) {
                    HorizontalSpacer(width = 16.dp)
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    textAlign = if (message.isFromUser) { TextAlign.End } else { TextAlign.Start },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (message.messageStatus == MessageStatus.Error) {
                                MaterialTheme.colorScheme.errorContainer
                            } else {
                                if (message.isFromUser) {
                                    MaterialTheme.colorScheme.secondaryContainer
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                }
                            }
                        )
                        .clickable(enabled = message.messageStatus == MessageStatus.Error) {
                            onResendMessage(message)
                        }
                        .padding(all = 8.dp)

                )
                if (!message.isFromUser) {
                    HorizontalSpacer(width = 16.dp)
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                }
            }
            if (message.messageStatus == MessageStatus.Sending) {
                Text(
                    text = stringResource(R.string.chat_message_loading),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                HorizontalSpacer(width = 32.dp)
            }
            if (message.messageStatus == MessageStatus.Error) {
                Row(
                    modifier = Modifier
                        .clickable {
                            onResendMessage(message)
                        }
                ) {
                    Box(
                        modifier = Modifier.weight(weight = 1f)
                    )
                    Text(
                        text = stringResource(R.string.chat_message_error),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            VerticalSpacer(height = 8.dp)
        }
    }
}