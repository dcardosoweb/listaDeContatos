package com.picpay.desafio.android.provider

import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse

object MockServiceResponseProvider {

    private const val SUCCESS_RESPONSE_CODE = 200

    fun usersContactMockResponse() = MockResponse().setResponseCode(SUCCESS_RESPONSE_CODE)
        .setBody(Gson().toJson(MockResponseDataProvider.mockedRepositoryListContacts()))
}