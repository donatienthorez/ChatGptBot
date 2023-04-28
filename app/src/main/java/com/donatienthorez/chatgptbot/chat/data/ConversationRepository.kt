package com.donatienthorez.chatgptbot.chat.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ConversationRepository {

    private var messagesList = mutableListOf(
        Message(
            text = "Hi, how can I help?",
            isFromUser = false,
            messageStatus = MessageStatus.Sent
        )
    )

    private val _conversationFlow = MutableStateFlow(
        value = Conversation(list = messagesList)
    )
    val conversationFlow = _conversationFlow.asStateFlow()

    fun addMessage(message: Message) : Conversation {
        messagesList.add(message)
        return updateConversationFlow(messagesList)
    }

    fun resendMessage(message: Message) : Conversation {
        messagesList.remove(message)
        messagesList.add(message)
        return updateConversationFlow(messagesList)
    }

    fun setMessageStatusToSent(messageId: String) {
        val index = messagesList.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messagesList[index] = messagesList[index].copy(messageStatus = MessageStatus.Sent)
        }

        updateConversationFlow(messagesList)
    }

    fun setMessageStatusToError(messageId: String) {
        val index = messagesList.indexOfFirst { it.id == messageId }
        if (index != -1) {
            messagesList[index] = messagesList[index].copy(messageStatus = MessageStatus.Error)
        }

        updateConversationFlow(messagesList)
    }

    private fun updateConversationFlow(messagesList: List<Message>) : Conversation {
        val conversation = Conversation(
            list = messagesList
        )
        _conversationFlow.value = conversation

        return conversation
    }
}

class Conversation(
    val list: List<Message>
)

data class Message(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isFromUser: Boolean,
    val messageStatus: MessageStatus = MessageStatus.Sending
)

sealed class MessageStatus {
    object Sending: MessageStatus()
    object Error: MessageStatus()
    object Sent: MessageStatus()
}