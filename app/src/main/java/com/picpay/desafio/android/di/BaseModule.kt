package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.BaseService.getCache
import com.picpay.desafio.android.core.BaseService.getOkHttpClient
import com.picpay.desafio.android.core.BaseService.getRetrofit
import com.picpay.desafio.android.data.irepository.IUserRepository
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.service.IUsersService
import com.picpay.desafio.android.presentation.viewModel.UserContactsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val coreModule = module {

    single { getCache(get()) }
    single { getOkHttpClient(get())}
}

val viewModule = module {
    viewModel {
        UserContactsViewModel(repository = get())
    }
}

val repositoryModule = module {
    factory<IUserRepository> {
        UserRepository(service = get())
    }
}

val serviceModule = module {
    factory { getRetrofit(get()) }
    factory { get<Retrofit>().create(IUsersService::class.java) }
}