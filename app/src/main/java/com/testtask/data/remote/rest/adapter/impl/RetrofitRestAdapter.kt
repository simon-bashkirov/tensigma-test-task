package com.testtask.data.remote.rest.adapter.impl

import com.testtask.data.remote.rest.adapter.RestAdapter
import com.testtask.data.repository.TokenProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitRestAdapter(private val apiBaseUrl: String) :
    RestAdapter {

    override fun <Service> createUnauthorizedService(serviceClass: Class<Service>) =
        OkHttpClient.Builder()
            .build()
            .let { client ->
                buildService(client, serviceClass)
            }

    override fun <Service> createAuthorizedService(
        serviceClass: Class<Service>,
        tokenProvider: TokenProvider
    ) =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${tokenProvider.getToken()}")
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
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(serviceClass)


}