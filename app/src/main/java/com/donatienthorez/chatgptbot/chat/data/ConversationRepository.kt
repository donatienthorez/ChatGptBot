package com.donatienthorez.chatgptbot.chat.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ConversationRepository {

    private val _messagesFlow = MutableStateFlow(value = Conversation(list = emptyList()))
    val messagesFlow = _messagesFlow.asStateFlow()

    fun addMessage(newMessage: String) {
        val messages = _messagesFlow.value

        val messagesList = mutableListOf<Message>()
        messagesList.addAll(messages.list)
        messagesList.add(
            Message(
                text = newMessage,
                fromUser = true
            )
        )

        _messagesFlow.value = Conversation(
            list = messagesList
        )
    }
}

class Conversation(
    val list: List<Message>
)

data class Message(
    val text: String,
    val fromUser: Boolean
)