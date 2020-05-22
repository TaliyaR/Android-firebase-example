package com.example.firebaseexample.ui.homePage.rv

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.firebaseexample.data.Item

object DiffUtilItem : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem.name == newItem.name

    override fun getChangePayload(oldItem: Item, newItem: Item): Any? {
        val diffBundle = Bundle()
        if (oldItem.name != newItem.name) {
            diffBundle.putString("name", newItem.name)
        }
        return if (diffBundle.isEmpty) null else diffBundle
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
        oldItem == newItem

}
