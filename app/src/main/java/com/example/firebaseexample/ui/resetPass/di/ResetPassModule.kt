package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.data.repository.AuthRepositoryImpl
import com.example.firebaseexample.ui.resetPass.ResetPasswordPresenter
import dagger.Module
import dagger.Provides

@Module
class ResetPassModule {

    @ResetPassScope
    @Provides
    fun provideResetPassPresenter(authRepository: AuthRepositoryImpl): ResetPasswordPresenter =
        ResetPasswordPresenter(authRepository)
}
