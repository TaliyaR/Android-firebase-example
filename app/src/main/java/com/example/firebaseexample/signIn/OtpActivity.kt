package com.example.firebaseexample.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseexample.R
import com.example.firebaseexample.homePage.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_otp.*

class OtpActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "OtpActivity"
    }

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        val mAuthVerificationId = intent.getStringExtra("AuthCredentials")
        firebaseAuth = FirebaseAuth.getInstance()

        btn_verify.setOnClickListener {
            val otp = otp.text.toString()
            if (otp.isEmpty()) {

            } else {
                signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(mAuthVerificationId, otp))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            toHome()
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    toHome()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {

                    }
                }
            }
    }

    private fun toHome() {
        startActivity(Intent(this@OtpActivity, HomeActivity::class.java))
        finish()
    }
}
