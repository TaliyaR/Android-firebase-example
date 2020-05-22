package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.ui.signUp.SignUpActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SignUpModule::class])
@SignUpScope
interface SignUpComponent {

     fun inject(signUpActivity: SignUpActivity)

    @Subcomponent.Builder
    interface Builder{

        fun signUpModule(signUpModule: SignUpModule): Builder

        fun build(): SignUpComponent

    }
}
