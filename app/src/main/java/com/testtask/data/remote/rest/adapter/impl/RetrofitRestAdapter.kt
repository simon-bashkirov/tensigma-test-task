package com.testtask.data.remote.rest.adapter.impl

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.repository.auth.AuthTokenProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitRestAdapter(private val apiBaseUrl: String) :
    RestAdapter {

    override fun <Service> createUnauthorizedService(serviceClass: Class<Service>) =
        OkHttpClient.Builder()
            .addNetworkInterceptor( StethoInterceptor())
            .build()
            .let { client ->
                buildService(client, serviceClass)
            }

    override fun <Service> createAuthorizedService(
        serviceClass: Class<Service>,
        authTokenProvider: AuthTokenProvider
    ) =
        OkHttpClient.Builder()
            .addNetworkInterceptor( StethoInterceptor())
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
                buildService(client, serviceClass)
            }


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