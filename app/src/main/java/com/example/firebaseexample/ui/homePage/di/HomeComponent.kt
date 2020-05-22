package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.ui.homePage.HomeActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [HomeModule::class])
@HomeScope
interface HomeComponent {

    fun inject(homeActivity: HomeActivity)

    @Subcomponent.Builder
    interface Builder {

        fun homeModule(homeModule: HomeModule): Builder

        fun build(): HomeComponent

    }
}
