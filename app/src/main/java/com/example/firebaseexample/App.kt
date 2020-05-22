package com.example.firebase_example

import android.app.Application
import com.example.firebaseexample.di.Injector
import moxy.MvpFacade

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvpFacade.init()
        Injector.init(this)
    }
}
