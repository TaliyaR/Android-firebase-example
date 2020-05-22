package com.example.firebaseexample.ui.resetPass

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.firebaseexample.R
import com.example.firebaseexample.di.Injector
import kotlinx.android.synthetic.main.activity_reset_password.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import javax.inject.Provider

class ResetPasswordActivity : MvpAppCompatActivity(), ResetPasswordView {

    companion object {
        fun start(@NonNull activity: Activity) {
            activity.startActivity(Intent(activity, ResetPasswordActivity::class.java))
        }
    }
    @Inject
    @InjectPresenter
    lateinit var presenterProvider: Provider<ResetPasswordPresenter>

    private val  presenter: ResetPasswordPresenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        Injector.plusResetPassComponent().inject(this)
        initClickListeners()
    }

    private fun initClickListeners() {
        btn_send.setOnClickListener {
            presenter.resetPassword(userEmail.text.toString())
        }
    }


    override fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

}

