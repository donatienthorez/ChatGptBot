package com.donatienthorez.chatgptbot.di

import com.donatienthorez.chatgptbot.chat.data.ConversationRepository
import com.donatienthorez.chatgptbot.chat.data.api.OpenAIRepository
import com.donatienthorez.chatgptbot.chat.domain.usecase.AddMessageUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.ObserveMessagesUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.SendPromptUseCase
import com.donatienthorez.chatgptbot.chat.ui.ChatViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val chatModule = module {
    viewModel {
        ChatViewModel(get(), get(), get())
    }
    single { OpenAIRepository(openAI = get()) }
    single { ConversationRepository() }

    single { SendPromptUseCase(openAIRepository = get()) }
    single { AddMessageUseCase(conversationRepository = get()) }
    single { ObserveMessagesUseCase(conversationRepository = get()) }
}