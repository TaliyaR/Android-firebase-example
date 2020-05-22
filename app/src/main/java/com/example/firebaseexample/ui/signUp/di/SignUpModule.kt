package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.data.repository.AuthRepositoryImpl
import com.example.firebaseexample.ui.signUp.SignUpPresenter
import dagger.Module
import dagger.Provides

@Module
class SignUpModule {

    @Provides
    @SignUpScope
    fun provideSignUpPresenter(authRepository: AuthRepositoryImpl): SignUpPresenter =
        SignUpPresenter(authRepository)
}
