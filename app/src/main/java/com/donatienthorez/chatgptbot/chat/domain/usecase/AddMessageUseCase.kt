package com.donatienthorez.chatgptbot.chat.domain.usecase

import com.donatienthorez.chatgptbot.chat.data.ConversationRepository

class AddMessageUseCase(
    private val conversationRepository: ConversationRepository
) {

    operator fun invoke(
        message: String
    ) = conversationRepository.addMessage(newMessage = message)

}