package com.example.firebaseexample.resetPass

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseexample.R
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)


        btn_send.setOnClickListener {
            val email = userEmail.text.toString().trim()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter your email!", Toast.LENGTH_SHORT).show()
            } else {

                firebaseAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this, "Password send to your email", Toast.LENGTH_SHORT
                            )
                                .show()

                            val bundle = Bundle()
                            bundle.putString(FirebaseAnalytics.Param.METHOD, "send password")
                            firebaseAnalytics.logEvent("forgot_password", bundle)
                        } else {
                            Toast.makeText(
                                this, task.exception?.message, Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

            }
        }
    }
}

