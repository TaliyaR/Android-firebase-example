package com.example.firebaseexample.ui.resetPass

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ResetPasswordView : MvpView {

    fun showMessage(string: String)

}
