package com.example.firebaseexample.ui.signIn.di

import com.example.firebaseexample.ui.resetPass.ResetPasswordActivity
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ResetPassModule::class])
@ResetPassScope
interface ResetPassComponent {

    fun inject(resetPasswordActivity: ResetPasswordActivity)

    @Subcomponent.Builder
    interface Builder {

        fun resetPassModule(resetPassModule: ResetPassModule): Builder

        fun build(): ResetPassComponent

    }
}
