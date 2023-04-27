package com.donatienthorez.chatgptbot

import android.app.Application
import com.donatienthorez.chatgptbot.di.chatModule
import com.donatienthorez.chatgptbot.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ChatGptBotApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin()
    }

    private fun startKoin() {
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@ChatGptBotApplication)
            // Load modules
            modules(
                listOf(
                    networkModule,
                    chatModule
                )
            )
        }
    }
}