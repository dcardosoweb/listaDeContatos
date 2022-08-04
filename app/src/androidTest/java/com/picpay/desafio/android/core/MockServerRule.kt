package com.picpay.desafio.android.core

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockServerRule : TestWatcher() {

    lateinit var server: MockWebServer

    override fun starting(description: Description) {
        server = MockWebServer()
        server.start(port = 8080)
    }

    override fun finished(description: Description) {
        server.shutdown()
    }
}