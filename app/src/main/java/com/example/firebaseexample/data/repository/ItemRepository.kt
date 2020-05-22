package com.example.firebaseexample.data.repository

import com.example.firebaseexample.data.Item

interface ItemRepository {

    fun addItem(string: String)

    fun getList(): List<Item>
}
