package com.picpay.desafio.android.provider

import com.picpay.desafio.android.data.service.response.GetUsersResponse
import com.picpay.desafio.android.presentation.model.UserContactModel
import kotlinx.coroutines.flow.flowOf


object MockResponseDataProvider {

    fun mockedUserContact() = internalMockedContact()
    private fun internalMockedContact() = GetUsersResponse(
        id = 1,
        userImage = "https://randomuser.me/api/portraits/men/1.jpg",
        name = "Alan da silva",
        userName =  "@alan.silva"
    )

    fun mockedRepositoryListContacts() = listOf(
        internalMockedContact(),
        GetUsersResponse(
            id = 2,
            userImage = "https://randomuser.me/api/portraits/men/2.jpg",
            name = "Bruno da silva",
            userName = "@bruno.silva"
        ),
        GetUsersResponse(
            id = 3,
            userImage = "https://randomuser.me/api/portraits/women/1.jpg",
            name = "Carla Silva",
            userName = "@carla.silva"
        ),
        GetUsersResponse(
            id = 4,
            userImage = "https://randomuser.me/api/portraits/men/3.jpg",
            name = "Daniel Silva",
            userName = "@daniel.silva"
        ),
        GetUsersResponse(
            id = 5,
            userImage = "https://randomuser.me/api/portraits/women/2.jpg",
            name = "Erika Silva",
            userName = "@erica.silva"
        )
    )
}