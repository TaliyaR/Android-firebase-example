package com.example.firebaseexample.ui.signUp

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface SignUpView: MvpView {

    fun showSignInPage()

    fun showHomePage()

    fun showMessage(string: String)

}
