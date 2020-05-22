package com.example.firebaseexample.di.module

import android.content.Context
import com.example.firebase_example.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: App): Context = app.applicationContext
}
