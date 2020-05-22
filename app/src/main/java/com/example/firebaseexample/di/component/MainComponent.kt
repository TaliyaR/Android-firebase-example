package com.example.firebaseexample.di.component

import com.example.firebaseexample.di.module.MainModule
import com.example.firebaseexample.di.scope.MainScope
import com.example.firebaseexample.ui.signIn.di.HomeComponent
import com.example.firebaseexample.ui.signIn.di.ResetPassComponent
import com.example.firebaseexample.ui.signIn.di.SignInComponent
import com.example.firebaseexample.ui.signIn.di.SignUpComponent
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    fun plusSignUpComponent(): SignUpComponent.Builder

    fun plusSignInComponent(): SignInComponent.Builder

    fun plusHomeComponent(): HomeComponent.Builder

    fun plusResetPassComponent(): ResetPassComponent.Builder

    @Subcomponent.Builder
    interface Builder {

        fun mainModule(mainModule: MainModule): Builder

        fun build(): MainComponent
    }
//    fun inject(otpActivity: OtpActivity)
}
