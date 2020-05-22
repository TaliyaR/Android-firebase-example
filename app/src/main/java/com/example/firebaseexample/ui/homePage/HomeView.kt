package com.example.firebaseexample.ui.homePage

import com.example.firebaseexample.data.Item
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface HomeView: MvpView {

    fun updateList(list: List<Item>)

    fun signOut()

    fun showMessage(string: String)
}
