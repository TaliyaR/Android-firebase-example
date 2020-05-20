package com.example.firebaseexample.homePage.rv

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.example.firebaseexample.data.Item

class DiffUtilCallBack(
    private val oldList: List<Item>,
    private val newList: List<Item>
): DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].name == newList[newItemPosition].name

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val diffBundle = Bundle()
        if (oldList[oldItemPosition].name != newList[newItemPosition].name) {
            diffBundle.putString("name", newList[newItemPosition].name)
        }
        return if (diffBundle.isEmpty) null else diffBundle
    }
}
