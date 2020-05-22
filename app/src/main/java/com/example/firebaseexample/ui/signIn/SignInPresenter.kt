package com.example.firebaseexample.ui.signIn

import android.text.TextUtils
import com.example.firebaseexample.data.repository.AuthRepositoryImpl
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject


class SignInPresenter @Inject constructor(
    private val repository: AuthRepositoryImpl
) : MvpPresenter<SignInView>() {

    val RC_SIGN_IN: Int = 1

    fun getCurrentUser() {
        repository.currentUser()
    }

    fun googleSignIn() {
        presenterScope.launch {
            viewState.navigateToGoogle()
        }
    }

    fun emailSignIn(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            viewState.showMessage("Enter email address!")
        }else if (TextUtils.isEmpty(password)) {
            viewState.showMessage("Enter password!")
        }else {
            presenterScope.launch {
                val success = repository.emailSignIn(email, password)
                if (success) {
                    viewState.showHomePage()
                } else {
                    viewState.showMessage("Error")
                }
            }
        }
    }

//    fun resetPassword(email: String) {
//        presenterScope.launch {
//            val success = repository.resetPassword(email)
//            if (success) {
//                viewState.showMessage("Password send to your email")
//            } else {
//                viewState.showMessage("Error")
//            }
//        }
//    }

    fun loginByNumber(code: String, number: String) {
        val num = "+$code$number"
        presenterScope.launch {
            val success = repository.signInWithPhone(num, callbacks)
        }

    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            viewState.showOtpPage()
        }

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            if (e is FirebaseAuthInvalidCredentialsException) {
                viewState.showMessage("Auth invalid.")
            } else if (e is FirebaseTooManyRequestsException) {
                viewState.showMessage("Quota exceeded.")
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        presenterScope.launch {
            val success = repository.signInWithPhoneAuthCredential(credential)
            if (success) {
                viewState.showHomePage()
            } else {
                viewState.showMessage("signInWithCredential:failure")
            }
        }
    }

    fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        presenterScope.launch {
            val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
            val success = repository.signInWithGoogle(credential)
            if (success) {
                viewState.showHomePage()
            } else {
                viewState.showMessage("Google sign in failed")
            }
        }
    }


}
