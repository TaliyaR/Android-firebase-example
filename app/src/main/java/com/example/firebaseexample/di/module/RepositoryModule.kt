package com.example.firebaseexample.di.module

import com.example.firebaseexample.data.repository.AuthRepository
import com.example.firebaseexample.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authRepository: AuthRepository): AuthRepository =
        authRepository

    @Provides
    @Singleton
    fun provideItemRepository(itemRepository: ItemRepository): ItemRepository =
        itemRepository

}
