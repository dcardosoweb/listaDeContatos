package com.picpay.desafio.android.di

import com.picpay.desafio.android.core.BaseService.getCache
import com.picpay.desafio.android.core.BaseService.getOkHttpClient
import com.picpay.desafio.android.core.BaseService.getRetrofit
import com.picpay.desafio.android.repository.service.IUsersService
import org.koin.dsl.module
import retrofit2.Retrofit

val coreModule = module {

    single { getCache(get()) }
    single { getOkHttpClient(get())}
}

val viewModule = module {

}

val repositoryModule = module {

}

val serviceModule = module {
    factory { getRetrofit(get()) }
    factory { get<Retrofit>().create(IUsersService::class.java) }
}