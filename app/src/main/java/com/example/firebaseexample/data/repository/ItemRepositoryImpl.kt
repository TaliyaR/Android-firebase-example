package com.example.firebaseexample.data.repository

import com.example.firebaseexample.data.Item
import com.example.firebaseexample.data.ListItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepositoryImpl @Inject constructor(
    private val list: ListItem
): ItemRepository {

    override fun addItem(string: String) {
        list.add(Item(string))
    }

    override fun getList() = list.getList()
}
