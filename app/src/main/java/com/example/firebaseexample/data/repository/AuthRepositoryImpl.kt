package com.example.firebaseexample.data.repository

import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    val fireBaseAuth: FirebaseAuth,
    val phoneAuthProvider: PhoneAuthProvider,
    val crashlytics: Crashlytics,
    val firebaseAnalytics: FirebaseAnalytics
) : AuthRepository {

    override fun currentUser() = fireBaseAuth.currentUser

    override fun crash() {
        crashlytics.crash()
    }

    override fun signOut() {
        fireBaseAuth.signOut()
    }

    override suspend fun signInWithGoogle(credential: AuthCredential) =
        withContext(Dispatchers.IO) {
            try {
                fireBaseAuth.signInWithCredential(credential)
                true
            } catch (e: FirebaseAuthException) {
                false
            }
        }

    override suspend fun emailSignIn(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                fireBaseAuth.signInWithEmailAndPassword(email, password)
                true
            } catch (e: FirebaseException) {
                false
            }
        }

    override suspend fun resetPassword(email: String) =
        withContext(Dispatchers.IO) {
            try {
                fireBaseAuth.sendPasswordResetEmail(email)
                true
            } catch (e: FirebaseException) {
                false
            }
        }

    override suspend fun signInWithPhone(
        number: String,
        callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    ) =
        withContext(Dispatchers.IO) {
            try {
                phoneAuthProvider.verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    callbacks
                )
                true
            } catch (e: FirebaseException) {
                false
            }
        }

    override suspend fun signUp(email: String, password: String) =
        withContext(Dispatchers.IO) {
            try {
                fireBaseAuth.createUserWithEmailAndPassword(email, password)
                true
            } catch (e: FirebaseException) {
                false
            }
        }

    override suspend fun setAnalytics(string: String, bundle: Bundle) {
        withContext(Dispatchers.IO) {
            try {
                firebaseAnalytics.logEvent("forgot_password", bundle)
            } catch (e: FirebaseException) {
            }
        }
    }

    override suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) =
        withContext(Dispatchers.IO) {
            try {
                fireBaseAuth.signInWithCredential(credential)
                true
            } catch (e: FirebaseAuthException) {
                false
            }
        }


//    override suspend fun setMobileAds(): Boolean {
//        withContext(Dispatchers.IO){
//            try{
//
//            }
//        }
//    }
}
