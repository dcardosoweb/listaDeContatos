package com.picpay.desafio.android.core

import android.content.Context
import com.blankj.utilcode.util.NetworkUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object BaseService {

    private const val MAJOR_TIMEOUT: Long = 300
    private const val MINOR_TIMEOUT: Long = 60
    private const val HEADER_PRAGMA = "Pragma"
    private const val HEADER_CACHE_CONTROL = "Cache-Control"
    private const val CACHE_SIZE = (5 * 1024 * 1024).toLong()
    private const val baseUrl = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    fun getCache(context: Context) = Cache(context.cacheDir, CACHE_SIZE)

    private val gson: Gson = GsonBuilder()
        .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
        .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    private var offlineInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            var request: Request = chain.request()
            if (NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_NO ||
                NetworkUtils.getNetworkType() == NetworkUtils.NetworkType.NETWORK_UNKNOWN) {
                val maxStale = 60 * 60 * 24 * 70 // Offline cache for 7 dias
                request = request.newBuilder()
                    .header(HEADER_CACHE_CONTROL, "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader(HEADER_PRAGMA)
                    .build()
            }
            return chain.proceed(request)
        }
    }

    private var onlineInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val response: okhttp3.Response = chain.proceed(chain.request())
            val maxAge = 60 // read from cache for 60 seconds
            return response.newBuilder()
                .header(HEADER_CACHE_CONTROL, "public, max-age=$maxAge")
                .removeHeader(HEADER_PRAGMA)
                .build()
        }
    }

    private val headerInterceptor = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        chain.proceed(requestBuilder.build())
    }

    fun getOkHttpClient(cache: Cache): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .addInterceptor(offlineInterceptor)
            .addInterceptor(headerInterceptor)
            .addNetworkInterceptor(onlineInterceptor)
            .cache(cache)
            .also {
                it.connectTimeout(MAJOR_TIMEOUT, TimeUnit.SECONDS)
                it.readTimeout(MINOR_TIMEOUT, TimeUnit.SECONDS)
                it.writeTimeout(MINOR_TIMEOUT, TimeUnit.SECONDS)
            }

    fun getRetrofit(client : OkHttpClient.Builder): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}