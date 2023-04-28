package com.donatienthorez.chatgptbot.chat.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donatienthorez.chatgptbot.chat.data.Conversation
import com.donatienthorez.chatgptbot.chat.domain.usecase.AddMessageUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.ObserveMessagesUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.SendPromptUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    val sendPromptUseCase: SendPromptUseCase,
    val observeMessagesUseCase: ObserveMessagesUseCase,
    val addMessageUseCase: AddMessageUseCase
) : ViewModel() {

    private val _messagesList = MutableLiveData<Conversation>()
    val messagesList: LiveData<Conversation> = _messagesList

    init {
        observeMessageList()
    }

    private fun observeMessageList() {
        viewModelScope.launch {
            observeMessagesUseCase.invoke().collect {
                _messagesList.postValue(it)
            }
        }
    }

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            addMessageUseCase(prompt)
        }
    }
}