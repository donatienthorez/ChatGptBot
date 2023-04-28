package com.donatienthorez.chatgptbot.di

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

val networkModule = module {
    single { provideOpenAI() }
}

fun provideOpenAI() : OpenAI {
    val config = OpenAIConfig(
        token = "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",
        timeout = Timeout(
            request = 20.seconds,
            connect = 20.seconds,
            socket = 20.seconds
        ),
    )

    return OpenAI(config)

}

