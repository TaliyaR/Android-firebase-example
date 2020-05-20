package com.example.firebaseexample.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebaseexample.homePage.HomeActivity
import com.example.firebaseexample.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.container
import kotlinx.android.synthetic.main.activity_sign_in.email
import kotlinx.android.synthetic.main.activity_sign_in.password
import kotlinx.android.synthetic.main.activity_sign_in.ti_email
import kotlinx.android.synthetic.main.activity_sign_in.ti_password
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()
        initClickListeners()
    }

    private fun initClickListeners() {
        btn_to_signin.setOnClickListener { finish() }
        btn_signup.setOnClickListener {
            val email = email.text.toString().trim { it <= ' ' }
            val password = password.text.toString().trim { it <= ' ' }
            if (TextUtils.isEmpty(email)) {
                ti_email.error = getString(R.string.error_email)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                ti_password.error = getString(R.string.error_pass)
                return@setOnClickListener
            }
            if (password.length < 6) {
                ti_password.error = getString(R.string.error_pass_length)
                return@setOnClickListener
            }
            //create user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@SignUpActivity) { task ->
                    Toast.makeText(this@SignUpActivity,
                        "createUserWithEmail:onComplete:" + task.isSuccessful, Toast.LENGTH_SHORT).show()
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Snackbar.make(container, "Authentication failed." + task.exception!!, Snackbar.LENGTH_SHORT).show()
                    } else {
                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                        finish()
                    }
                }
        }
    }
}
