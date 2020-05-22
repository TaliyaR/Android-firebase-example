package com.example.firebaseexample.ui.signIn

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.firebaseexample.R
import com.example.firebaseexample.di.Injector
import com.example.firebaseexample.ui.homePage.HomeActivity
import com.example.firebaseexample.ui.resetPass.ResetPasswordActivity
import com.example.firebaseexample.ui.signUp.SignUpActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_sign_in.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import javax.inject.Provider

class SignInActivity : MvpAppCompatActivity(), SignInView {

    companion object {
        private const val TAG = "SignInActivity"

        fun start(@NonNull activity: Activity) {
            activity.startActivity(Intent(activity, SignInActivity::class.java))
        }
    }
    @Inject
    @InjectPresenter
    lateinit var presenterProvider: Provider<SignInPresenter>

    private val presenter: SignInPresenter by moxyPresenter {
        presenterProvider.get()
    }
    val RC_SIGN_IN: Int = 1

    @Inject
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        Injector.plusSignInComponent().inject(this)
        if(presenter.getCurrentUser() != null){
            HomeActivity.start(this)
        }
        initClickListeners()
    }

    private fun initClickListeners() {
        btn_to_signup.setOnClickListener {
            SignUpActivity.start(this)
        }
        btn_reset_password.setOnClickListener {
            ResetPasswordActivity.start(this)
        }
        btn_num_login.setOnClickListener {
            presenter.loginByNumber(country_code.text.toString(), number.text.toString())
        }
        google_button.setOnClickListener {
            presenter.googleSignIn()
        }
        btn_login.setOnClickListener {
            presenter.emailSignIn(et_email.text.toString(), et_password.text.toString())
        }
    }

    override fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun showHomePage() {
        HomeActivity.start(this)
    }

    override fun showOtpPage() {
        OtpActivity.start(this)
    }

    override fun navigateToGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)?.let {
                    presenter.firebaseAuthWithGoogle(it)
                } ?: run {
                    Toast.makeText(this@SignInActivity, "Sign in error", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Toast.makeText(this@SignInActivity, "Google sign in failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
