package com.example.firebaseexample.data

object ListItem {
    private val list = ArrayList<Item>()

    init {
        list.add(Item("ssssss"))
        list.add(Item("ssssss"))
        list.add(Item("ssssss"))
        list.add(Item("ssssss"))
        list.add(Item("ssssss"))
        list.add(Item("ssssss"))
    }

    fun getList() = list

    fun add(item: Item){
        list.add(item)
    }
}
