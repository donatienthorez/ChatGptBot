package com.donatienthorez.chatgptbot.chat.domain.usecase

import com.aallam.openai.api.completion.TextCompletion
import com.donatienthorez.chatgptbot.chat.data.api.OpenAIRepository

class SendPromptUseCase(
    private val openAIRepository: OpenAIRepository
) {

    suspend operator fun invoke(
        prompt: String
    ) : TextCompletion = openAIRepository.sendPrompt(prompt)
}