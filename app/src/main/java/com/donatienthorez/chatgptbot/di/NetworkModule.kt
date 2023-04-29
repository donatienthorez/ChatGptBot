package com.donatienthorez.chatgptbot.di

import com.aallam.openai.api.http.Timeout
import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.donatienthorez.chatgptbot.BuildConfig
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

val networkModule = module {
    single { provideOpenAI() }
}

fun provideOpenAI() : OpenAI {
    val config = OpenAIConfig(
        token = BuildConfig.OPEN_AI_API_KEY,
        timeout = Timeout(
            request = 20.seconds,
            connect = 20.seconds,
            socket = 20.seconds
        ),
    )

    return OpenAI(config)

}

