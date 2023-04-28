package com.donatienthorez.chatgptbot.chat.domain.usecase

import com.donatienthorez.chatgptbot.chat.data.ConversationRepository
import com.donatienthorez.chatgptbot.chat.data.Message
import com.donatienthorez.chatgptbot.chat.data.MessageStatus
import com.donatienthorez.chatgptbot.chat.data.api.OpenAIRepository

class ResendMessageUseCase(
    private val openAIRepository: OpenAIRepository,
    private val conversationRepository: ConversationRepository
) {

    suspend operator fun invoke(
        message: Message
    ) {
        val conversation = conversationRepository.resendMessage(message)

        try {
            val reply = openAIRepository.sendChatRequest(conversation)
            conversationRepository.setMessageStatusToSent(message.id)
            conversationRepository.addMessage(reply)
        } catch (exception: Exception) {
            conversationRepository.setMessageStatusToError(message.id)
        }
    }
}