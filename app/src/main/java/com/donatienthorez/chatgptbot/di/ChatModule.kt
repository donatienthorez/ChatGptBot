package com.donatienthorez.chatgptbot.di

import com.donatienthorez.chatgptbot.chat.ui.ChatViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val chatModule = module {
    viewModel {
        ChatViewModel()
    }
}