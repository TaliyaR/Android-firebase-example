package com.example.firebaseexample.di.component

import android.content.Context
import com.example.firebase_example.App
import com.example.firebaseexample.di.module.AppModule
import com.example.firebaseexample.di.module.FirebaseModule
import com.example.firebaseexample.di.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, FirebaseModule::class, RepositoryModule::class])
interface AppComponent {

    fun mainComponent(): MainComponent.Builder
    fun provideApp(): Context

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }
}
