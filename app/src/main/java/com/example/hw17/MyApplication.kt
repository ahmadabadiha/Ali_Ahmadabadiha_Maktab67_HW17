package com.example.hw17

import android.app.Application

class MyApplication : Application() {

    val appContainer by lazy { AppContainer(this) }
}