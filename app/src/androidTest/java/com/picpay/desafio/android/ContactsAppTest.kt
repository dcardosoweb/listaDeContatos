package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.core.TestModuleInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ContactsAppTest : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ContactsAppTest)
        }
        TestModuleInitializer.init()
    }

}