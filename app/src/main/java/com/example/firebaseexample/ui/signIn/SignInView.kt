package com.example.firebaseexample.ui.signIn

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface SignInView : MvpView {

    fun navigateToGoogle()

    fun showMessage(string: String)

    fun showHomePage()

    fun showOtpPage()

}
