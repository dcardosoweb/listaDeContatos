package com.picpay.desafio.android.data.irepository

import androidx.lifecycle.LiveData
import com.picpay.desafio.android.data.service.response.GetUsersResponse
import com.picpay.desafio.android.utils.Result

interface IUserRepository {
    val statusGetUsersFromServer: LiveData<Result<List<GetUsersResponse>>>

    suspend fun getUsersFromServer()
}