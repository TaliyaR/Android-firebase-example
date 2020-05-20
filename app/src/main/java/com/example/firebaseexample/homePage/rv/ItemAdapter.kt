package com.example.firebaseexample.homePage.rv

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseexample.data.Item
import com.example.firebaseexample.data.ListItem

class ItemAdapter(private val list: ArrayList<Item>
): RecyclerView.Adapter<ItemHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder.create(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) =
        holder.bind(list[position])

    fun update(){
        val diffCallBack = DiffUtilCallBack(
            list,
            ListItem.getList()
        )
        val  diffResult = DiffUtil.calculateDiff(diffCallBack)
        list.apply {
            clear()
            addAll(ListItem.getList())
        }
        diffResult.dispatchUpdatesTo(this)
    }
}
