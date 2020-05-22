package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.ui.signIn.SignInActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SignInModule::class])
@SignInScope
interface SignInComponent {

    fun inject(signInActivity: SignInActivity)

    @Subcomponent.Builder
    interface Builder {

        fun signInModule(signInModule: SignInModule): Builder

        fun build(): SignInComponent

    }
}
