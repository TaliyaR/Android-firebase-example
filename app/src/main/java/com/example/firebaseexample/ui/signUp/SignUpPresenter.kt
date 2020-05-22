package com.example.firebaseexample.ui.signUp

import android.text.TextUtils
import com.example.firebaseexample.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
    private val repository: AuthRepository
) : MvpPresenter<SignUpView>() {

    fun signUp(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            viewState.showMessage("Enter email address!")
        }else if (TextUtils.isEmpty(password)) {
            viewState.showMessage("Enter password!")
        }else if (password.length < 6) {
            viewState.showMessage("Password too short, enter minimum 6 characters!")
        } else {
            presenterScope.launch {
                if (withContext(Dispatchers.IO) {
                        repository.signUp(email, password)
                    }) {
                    viewState.showHomePage()
                } else {
                    viewState.showMessage("Authentication failed.")
                }
            }
        }
    }

    fun returnSignIn(){
        viewState.showSignInPage()
    }

}


