package com.donatienthorez.chatgptbot.chat.data.api

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.completion.TextCompletion
import com.aallam.openai.api.model.ModelId
import com.aallam.openai.client.OpenAI

class OpenAIRepository(private val openAI: OpenAI) {

    suspend fun sendPrompt(prompt: String) : TextCompletion {
        val completionRequest = CompletionRequest(
            model = ModelId("text-ada-001"),
            prompt = prompt,
            echo = false
        )

        return openAI.completion(completionRequest)
    }
}