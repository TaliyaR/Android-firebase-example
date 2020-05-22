package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.data.repository.AuthRepositoryImpl
import com.example.firebaseexample.ui.signIn.SignInPresenter
import dagger.Module
import dagger.Provides

@Module
class SignInModule {

    @SignInScope
    @Provides
    fun provideSignInPresenter(authRepository: AuthRepositoryImpl): SignInPresenter =
        SignInPresenter(authRepository)
}
