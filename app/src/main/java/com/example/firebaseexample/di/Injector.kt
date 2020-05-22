package com.example.firebaseexample.di

import com.example.firebase_example.App
import com.example.firebaseexample.di.component.AppComponent
import com.example.firebaseexample.di.component.MainComponent
import com.example.firebaseexample.di.module.MainModule
import com.example.firebaseexample.ui.signIn.di.*

object Injector {

    lateinit var appComponent: AppComponent
    private var mainComponent: MainComponent? = null
    private var signUpComponent: SignUpComponent? = null
    private var signInComponent: SignInComponent? = null
    private var homeComponent: HomeComponent? = null
    private var resetPassComponent: ResetPassComponent? = null

    fun init(app: App){
        appComponent = DaggerAppComponent.builder()
            .application(app)
            .build()
    }

    fun plusMainComponent(): MainComponent = mainComponent?: appComponent.mainComponent()
        .mainModule(MainModule())
        .build().also {
            mainComponent = it
        }

    fun clearMainComponent(){
        mainComponent = null
    }

    fun plusSignUpComponent(): SignUpComponent = signUpComponent ?: plusMainComponent()
        .plusSignUpComponent()
        .signUpModule(SignUpModule())
        .build().also {
            signUpComponent = it
        }

    fun clearSignUpComponent(){
        signUpComponent = null
    }

    fun plusSignInComponent(): SignInComponent = signInComponent ?: plusMainComponent()
        .plusSignInComponent()
        .signInModule(SignInModule())
        .build().also {
            signInComponent = it
        }

    fun clearSignInComponent() {
        signInComponent = null
    }


    fun plusHomeComponent(): HomeComponent = homeComponent ?: plusMainComponent()
        .plusHomeComponent()
        .homeModule(HomeModule())
        .build().also {
            homeComponent = it
        }

    fun clearHomeComponent(){
        homeComponent = null
    }

    fun plusResetPassComponent(): ResetPassComponent = resetPassComponent ?: plusMainComponent()
        .plusResetPassComponent()
        .resetPassModule(ResetPassModule())
        .build().also {
            resetPassComponent = it
        }

    fun clearResetPassComponent(){
        resetPassComponent = null
    }
}
