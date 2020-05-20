package com.example.firebaseexample.homePage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.example.firebaseexample.data.Item
import com.example.firebaseexample.data.ListItem
import com.example.firebaseexample.R
import com.example.firebaseexample.homePage.rv.ItemAdapter
import com.example.firebaseexample.signIn.SignInActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {

    companion object {

    }

    private lateinit var firebaseAuth: FirebaseAuth
    private var firebaseUser: FirebaseUser? = null
    private lateinit var username: String
    private lateinit var mAdView: AdView
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        MobileAds.initialize(this) {}
        mAdView = adView
        var adRequest = AdRequest.Builder()
            .build()
        mAdView.loadAd(adRequest)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)


        addNewItem()
    }

    override fun onStart() {
        super.onStart()
        if (firebaseUser == null) {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
            return
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.crash -> {
                Crashlytics.getInstance().crash()
                true
            }
            R.id.signout -> {
                signOut()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addNewItem() {
        val adapterList =
            ItemAdapter(ListItem.getList())
        rv_item.adapter = adapterList

        fab.setOnClickListener { view ->
            val dialogBuilder = AlertDialog.Builder(view.context)
            val dialogView = LayoutInflater.from(view.context)
                .inflate(R.layout.add_dialog, null)
            dialogBuilder.setView(dialogView)
                .setPositiveButton("Ok") { _, _ ->
                    ListItem.add(
                        Item(
                            dialogView.et_name.text.toString()
                        )
                    )
                    adapterList.update()
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.METHOD, firebaseUser?.uid)
                    firebaseAnalytics.logEvent("fab", bundle)
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
            dialogBuilder.show()
        }
    }

    private fun signOut() {
        startActivity(Intent(this, SignInActivity::class.java))
        FirebaseAuth.getInstance().signOut();
    }

}
