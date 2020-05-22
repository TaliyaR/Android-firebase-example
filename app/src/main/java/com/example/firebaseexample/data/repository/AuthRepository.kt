package com.example.firebaseexample.data.repository

import android.os.Bundle
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {

    fun currentUser(): FirebaseUser?

    fun crash()

    fun signOut()

    suspend fun signInWithGoogle(credential: AuthCredential): Boolean

    suspend fun emailSignIn(email: String, password: String): Boolean

    suspend fun resetPassword(email: String):Boolean

    suspend fun signInWithPhone(number: String, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks): Boolean

    suspend fun signUp(email: String, password: String): Boolean

    suspend fun setAnalytics(string: String, bundle: Bundle)

    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential): Boolean

//    suspend fun setMobileAds(): Boolean
}
