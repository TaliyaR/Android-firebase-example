package com.example.firebaseexample.ui.signUp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.firebaseexample.R
import com.example.firebaseexample.di.Injector
import com.example.firebaseexample.ui.homePage.HomeActivity
import com.example.firebaseexample.ui.signIn.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import javax.inject.Provider

class SignUpActivity : MvpAppCompatActivity(), SignUpView {

    companion object{
        fun start(@NonNull activity: Activity){
            activity.startActivity(Intent(activity, SignUpActivity::class.java))
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenterProvider: Provider<SignUpPresenter>

    private val  presenter: SignUpPresenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Injector.plusSignUpComponent().inject(this)
        initClickListeners()
    }

    private fun initClickListeners(){
        btn_signup.setOnClickListener{
            presenter.signUp(et_email.text.toString(), et_password.text.toString())
        }
        btn_to_signin.setOnClickListener(){
            presenter.returnSignIn()
        }
    }

    override fun showSignInPage() {
        SignInActivity.start(this)
    }

    override fun showHomePage() {
        HomeActivity.start(this)
    }

    override fun showMessage(string: String) {
        Toast.makeText(this@SignUpActivity, string, Toast.LENGTH_SHORT).show()
    }

}
