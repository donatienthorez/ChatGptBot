package com.donatienthorez.chatgptbot.chat.domain.usecase

import com.donatienthorez.chatgptbot.chat.data.Conversation
import com.donatienthorez.chatgptbot.chat.data.ConversationRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessagesUseCase(
    private val conversationRepository: ConversationRepository
) {

    operator fun invoke() : Flow<Conversation> = conversationRepository.messagesFlow

}