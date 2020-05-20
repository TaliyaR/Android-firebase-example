package com.example.firebaseexample.signIn

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseexample.R
import com.example.firebaseexample.homePage.HomeActivity
import com.example.firebaseexample.resetPass.ResetPasswordActivity
import com.example.firebaseexample.signUp.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.concurrent.TimeUnit

class SignInActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SignInActivity"
    }

    val RC_SIGN_IN: Int = 1
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions

    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        configureGoogleSignIn()
        setupUI()

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        if (firebaseAuth.currentUser != null) {
            toHome()
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                Log.d(TAG, "onCodeSent:$verificationId")
                val intent = Intent(this@SignInActivity, OtpActivity::class.java)
                intent.putExtra("AuthCredentials", verificationId)
                startActivity(intent)
                finish()
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "OnVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "OnVerificationFailed", e)
                if (e is FirebaseAuthInvalidCredentialsException) {
                    Snackbar.make(
                        findViewById(android.R.id.content), "Auth invalid.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else if (e is FirebaseTooManyRequestsException) {
                    Snackbar.make(
                        findViewById(android.R.id.content), "Quota exceeded.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


        initClickListeners()
    }

    private fun toHome(){
        startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
        finish()
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


    private fun configureGoogleSignIn() {
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, mGoogleSignInOptions)
    }

    private fun setupUI() {
        google_button.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)?.let {
                    firebaseAuthWithGoogle(it)
                } ?: run {
                    Toast.makeText(this@SignInActivity, "Sign in error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun initClickListeners() {
        btn_to_signup.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        btn_reset_password.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }

        btn_num_login.setOnClickListener{
            val country_code = country_code.text.toString()
            val phone_num = number.text.toString()
            val complete_phone_number = "+$country_code$phone_num"

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                complete_phone_number,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks
            )
        }

        btn_login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            val bundle = Bundle()

            if (TextUtils.isEmpty(email)) {
                ti_email.error = getString(R.string.error_email)
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                ti_password.error = getString(R.string.error_pass)
                return@setOnClickListener
            }

            //authenticate user
            firebaseAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener(this@SignInActivity) { task ->
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        // there was an error
                        if (password.length < 6) {
                            ti_password.error = getString(R.string.error_pass_length)
                        } else {
                            Snackbar.make(container,
                                R.string.error_signin, Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        toHome()
                    }
                }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.w(TAG, "signInWithCredential", it.exception)
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                } else {
                    Toast.makeText(this, "Google sign in failed:(", Toast.LENGTH_LONG).show()
                }
            }
    }
}
