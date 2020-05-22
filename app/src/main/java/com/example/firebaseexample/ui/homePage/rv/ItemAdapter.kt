package com.example.firebaseexample.ui.homePage.rv

import android.os.Bundle
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.firebaseexample.data.Item

class ItemAdapter(
    private var list: List<Item>
) : ListAdapter<Item, ItemHolder>(DiffUtilItem) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(list[position])

    override fun onBindViewHolder(holder: ItemHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val bundle = payloads[0] as? Bundle
            holder.updateFromBundle(bundle)
        }
    }

    fun update(newList: List<Item>) {
        list = newList
        submitList(list)
        notifyDataSetChanged()
    }
}
