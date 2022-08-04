package com.picpay.desafio.android.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.irepository.IUserRepository
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.data.service.IUsersService
import com.picpay.desafio.android.presentation.viewModel.UserContactsViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun Scope.getRetrofit() = get<Retrofit>()

object TestModuleInitializer {

    private val serviceModule = module {
        fun provideBaseUrl() = "http://localhost:8080"
        fun provideGson() = GsonBuilder().create()
        fun provideOkHttp() = OkHttpClient.Builder().build()
        fun provideRetrofit(baseUrl: String, okHttp: OkHttpClient, gson: Gson) = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        single { provideBaseUrl() }
        single { provideGson() }
        single { provideOkHttp() }
        single { provideRetrofit(get(), get(), get()) }

        factory { BaseService.getRetrofit(get()) }
        factory { get<Retrofit>().create(IUsersService::class.java) }
    }

    private val repositoryModule = module {
        factory<IUserRepository> {
            UserRepository(service = get())
        }
    }

    private val viewModelsModule = module {
        viewModel { UserContactsViewModel(get()) }
    }

    fun init() = loadKoinModules(
        listOf(serviceModule, repositoryModule, viewModelsModule)
    )
}