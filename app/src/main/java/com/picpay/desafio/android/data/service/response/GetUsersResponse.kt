package com.picpay.desafio.android.data.service.response

import com.google.gson.annotations.SerializedName
import com.picpay.desafio.android.presentation.model.UserContactModel

class GetUsersResponse (
    @SerializedName("id") val id: Int,
    @SerializedName("img") val userImage: String,
    @SerializedName("name") val name: String,
    @SerializedName("username") val userName: String
)

fun GetUsersResponse.asModel(): UserContactModel {
    return UserContactModel(
        id = id,
        image=userImage,
        name = name,
        userName = userName
    )
}

fun List<GetUsersResponse>.asListModel(): List<UserContactModel> {
    return map {
        it.asModel()
    }
}
