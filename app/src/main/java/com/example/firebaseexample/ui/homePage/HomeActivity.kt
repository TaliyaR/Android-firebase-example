package com.example.firebaseexample.ui.homePage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import com.example.firebaseexample.data.Item
import com.example.firebaseexample.R
import com.example.firebaseexample.di.Injector
import com.example.firebaseexample.ui.homePage.rv.ItemAdapter
import com.example.firebaseexample.ui.signIn.SignInActivity
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.add_dialog.view.*
import kotlinx.android.synthetic.main.toolbar.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import moxy.presenter.InjectPresenter
import javax.inject.Inject
import javax.inject.Provider

class HomeActivity : MvpAppCompatActivity(), HomeView {

    private lateinit var adapter: ItemAdapter

    companion object {
        fun start(@NonNull activity: Activity) {
            activity.startActivity(Intent(activity, HomeActivity::class.java))
        }
    }

    @Inject
    @InjectPresenter
    lateinit var presenterProvider: Provider<HomePresenter>

    private val presenter: HomePresenter by moxyPresenter {
        presenterProvider.get()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Injector.plusHomeComponent().inject(this)
        setSupportActionBar(toolbar)
        initRecycler()
        initClickListeners()

    }

    override fun onStart() {
        super.onStart()
        if (presenter.getCurrentUser()== null) {
            SignInActivity.start(this)
            finish()
            return
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearHomeComponent()
    }

    private fun initClickListeners() {
        fab.setOnClickListener {view ->
            addDialog(view)
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
                presenter.crash()
                true
            }
            R.id.signout -> {
                presenter.signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun signOut() {
        SignInActivity.start(this)
    }

    override fun showMessage(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    override fun updateList(list: List<Item>) {
        adapter.update(list)
    }

    private fun addDialog(view: View) {
        val dialogBuilder = AlertDialog.Builder(view.context)
        val dialogView = LayoutInflater.from(view.context)
            .inflate(R.layout.add_dialog, null)
        dialogBuilder.setView(dialogView)
            .setPositiveButton("Ok") { dialog, _ ->
                presenter.addNewItem(dialogView.et_name.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        dialogBuilder.create().show()
    }

    private fun initRecycler(){
        adapter = ItemAdapter(emptyList())
        rv_item.adapter = adapter
    }


}
