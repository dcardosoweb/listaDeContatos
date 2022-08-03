package com.picpay.desafio.android.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.picpay.desafio.android.data.irepository.IUserRepository
import com.picpay.desafio.android.data.service.IUsersService
import com.picpay.desafio.android.data.service.response.GetUsersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.picpay.desafio.android.utils.Result
import com.picpay.desafio.android.utils.responseError

class UserRepository(
    val service:IUsersService
    ):IUserRepository {

    private val _statusGetUsersFromServer =
        MutableLiveData<Result<List<GetUsersResponse>>>()
    override val statusGetUsersFromServer: LiveData<Result<List<GetUsersResponse>>>
        get() = _statusGetUsersFromServer

    override suspend fun getUsersFromServer() {
        withContext(Dispatchers.IO) {
            _statusGetUsersFromServer.postValue(Result.InProgress)
            try {
                val response = service.getUsers()

                if (response.isSuccessful) {
                    response.body()?.let {
                        _statusGetUsersFromServer.postValue(Result.Success(it))
                    }
                } else {
                    response.code().let {
                        _statusGetUsersFromServer.postValue(responseError(response.code()))
                    }
                }
            } catch (ex: Exception) {
                _statusGetUsersFromServer.postValue(
                    Result.Error(ex, null)
                )
            }
        }
    }

}