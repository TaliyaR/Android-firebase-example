package com.example.firebaseexample.homePage.rv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.data.Item
import com.example.firebaseexample.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item.view.*

class ItemHolder(
    override val containerView: View
):RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(item: Item){
        with(containerView) {
            tv_name.text = item.name
        }
    }

    companion object{
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
