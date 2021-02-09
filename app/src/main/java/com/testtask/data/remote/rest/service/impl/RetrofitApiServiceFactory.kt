package com.testtask.data.remote.rest.service.impl

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.testtask.data.remote.rest.service.ApiServiceFactory
import com.testtask.data.remote.rest.service.AuthorizedApiService
import com.testtask.data.remote.rest.service.UnauthorizedApiService
import com.testtask.data.repository.auth.AuthTokenProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitApiServiceFactory(
    private val apiBaseUrl: String,
    private val authTokenProvider: AuthTokenProvider
) :
    ApiServiceFactory {

    private val baseClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    private val _unauthorizedApiService: UnauthorizedApiService by lazy {
        buildService(baseClient, UnauthorizedApiService::class.java)
    }

    private val _authorizedApiService: AuthorizedApiService by lazy {
        baseClient
            .newBuilder()
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${authTokenProvider.getToken()}")
                    .build()
                chain.proceed(request)
            }
            .build()
            .let { client ->
                buildService(client, AuthorizedApiService::class.java)
            }
    }

    override fun getUnauthorizedService() = _unauthorizedApiService

    override fun getAuthorizedService() = _authorizedApiService


    private fun <Service> buildService(okHttpClient: OkHttpClient, serviceClass: Class<Service>) =
        Retrofit.Builder()
            .baseUrl(apiBaseUrl)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setLenient()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(serviceClass)
}