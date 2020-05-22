package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.data.repository.AuthRepositoryImpl
import com.example.firebaseexample.data.repository.ItemRepository
import com.example.firebaseexample.ui.homePage.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class HomeModule {

    @HomeScope
    @Provides
    fun provideHomePresenter(authRepository: AuthRepositoryImpl, itemRepository: ItemRepository): HomePresenter =
        HomePresenter(authRepository, itemRepository)
}
