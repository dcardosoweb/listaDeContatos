package com.picpay.desafio.android.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.irepository.IUserRepository
import kotlinx.coroutines.launch

class UserContactsViewModel(private val repository: IUserRepository):ViewModel() {

    val statusGetUsersFromServer = repository.statusGetUsersFromServer

    fun getUsersFromServer() {
        viewModelScope.launch {
            repository.getUsersFromServer()
        }
    }
}