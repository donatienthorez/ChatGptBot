package com.donatienthorez.chatgptbot.chat.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.donatienthorez.chatgptbot.chat.data.Conversation
import com.donatienthorez.chatgptbot.chat.data.Message
import com.donatienthorez.chatgptbot.chat.domain.usecase.ObserveMessagesUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.ResendMessageUseCase
import com.donatienthorez.chatgptbot.chat.domain.usecase.SendChatRequestUseCase
import kotlinx.coroutines.launch

class ChatViewModel(
    private val sendChatRequestUseCase: SendChatRequestUseCase,
    private val resendChatRequestUseCase: ResendMessageUseCase,
    private val observeMessagesUseCase: ObserveMessagesUseCase,
) : ViewModel() {

    private val _conversation = MutableLiveData<Conversation>()
    val conversation: LiveData<Conversation> = _conversation

    init {
        observeMessageList()
    }

    private fun observeMessageList() {
        viewModelScope.launch {
            observeMessagesUseCase.invoke().collect {
                _conversation.postValue(it)
            }
        }
    }

    fun sendMessage(prompt: String) {
        viewModelScope.launch {
            sendChatRequestUseCase(
                prompt
            )
        }
    }

    fun resendMessage(message: Message) {
        viewModelScope.launch {
            resendChatRequestUseCase(
                message
            )
        }
    }
}