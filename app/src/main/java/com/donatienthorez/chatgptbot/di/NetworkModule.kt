package com.donatienthorez.chatgptbot.di

import com.aallam.openai.client.OpenAI
import com.aallam.openai.client.OpenAIConfig
import com.donatienthorez.chatgptbot.BuildConfig
import org.koin.dsl.module

val networkModule = module {
    single { provideOpenAI() }
}

fun provideOpenAI() : OpenAI {
    val config = OpenAIConfig(
        token = BuildConfig.OPEN_AI_API_KEY
    )

    return OpenAI(config)

}

