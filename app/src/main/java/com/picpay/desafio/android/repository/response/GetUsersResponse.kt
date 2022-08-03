package com.picpay.desafio.android.repository.response

import com.google.gson.annotations.SerializedName

class GetUsersResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("img") val userImage: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val userName: String
)