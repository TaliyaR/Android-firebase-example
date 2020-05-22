package com.example.firebaseexample.ui.resetPass

import android.os.Bundle
import android.text.TextUtils
import com.example.firebaseexample.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class ResetPasswordPresenter @Inject constructor(
    private val repository: AuthRepository
): MvpPresenter<ResetPasswordView>(){

    fun resetPassword(email: String){
        if (TextUtils.isEmpty(email)) {
            viewState.showMessage("Enter your email!")
        } else {
            presenterScope.launch {
                if (withContext(Dispatchers.IO) {
                        repository.resetPassword(email)
                    }) {
                    val bundle = Bundle()
                    bundle.putString("method", "send password")
                    repository.setAnalytics("forgot_password", bundle)
                    viewState.showMessage("Password send to your email")
                } else {
                    viewState.showMessage("Failed")
                }
            }
        }
    }
}
