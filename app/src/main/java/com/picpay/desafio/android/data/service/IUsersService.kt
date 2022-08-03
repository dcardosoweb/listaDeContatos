package com.picpay.desafio.android.data.service

import com.picpay.desafio.android.data.service.response.GetUsersResponse
import retrofit2.Response
import retrofit2.http.GET

interface IUsersService {

    @GET("users")
    suspend fun getUsers(): Response<List<GetUsersResponse>>
}