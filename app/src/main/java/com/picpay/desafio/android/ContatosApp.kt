package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.coreModule
import com.picpay.desafio.android.di.repositoryModule
import com.picpay.desafio.android.di.serviceModule
import com.picpay.desafio.android.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ContatosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContatosApp)
            modules(
                listOf(
                    coreModule,
                    viewModule,
                    serviceModule,
                    repositoryModule
                )
            )
        }
    }

}