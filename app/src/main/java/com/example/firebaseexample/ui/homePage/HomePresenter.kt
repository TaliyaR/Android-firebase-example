package com.example.firebaseexample.ui.homePage

import com.example.firebaseexample.data.repository.AuthRepository
import com.example.firebaseexample.data.repository.ItemRepository
import kotlinx.coroutines.launch
import moxy.MvpPresenter
import moxy.presenterScope
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: AuthRepository,
    private val itemRepository: ItemRepository
) : MvpPresenter<HomeView>() {

    fun getCurrentUser() {
        repository.currentUser()
    }

    fun crash() {
        repository.crash()
    }

    fun signOut() {
        repository.signOut()
    }

    fun addNewItem(string: String) {
        presenterScope.launch {
            itemRepository.addItem(string)
            viewState.updateList(itemRepository.getList())
        }
    }


}
