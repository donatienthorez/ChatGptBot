package com.donatienthorez.chatgptbot.di

import com.aallam.openai.client.OpenAI
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val networkModule = module {
    single { provideOkhttpClient() }
    single { provideRetrofit(okHttpClient = get()) }

    single { provideOpenAI() }
}

fun provideOkhttpClient() = OkHttpClient.Builder().build()

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.openai.com/")
        .client(okHttpClient)
        .build()
}

fun provideOpenAI() = OpenAI("")

