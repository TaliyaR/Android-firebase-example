package com.example.firebaseexample.ui.homePage.rv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.R
import com.example.firebaseexample.data.Item
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.view.*

class ItemHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Item) {
        containerView.tv_name.text = item.name
    }

    fun updateFromBundle(bundle: Bundle?) {
        bind(Item(bundle?.getString(NAME) ?: ""))
    }

    companion object {
        const val NAME = "name"

        fun create(parent: ViewGroup) =
            ItemHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item,
                    parent,
                    false
                )
            )
    }
}
