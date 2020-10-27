package com.cairocart.Retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        private val BasuRl: String = "https://magento-364088-1142992.cloudwaysapps.com/"
        val BASE_URL2 = "https://secure5.tranzila.com/cgi-bin/"
         var retrofi2: Retrofit? = null

        private var retrofit: Retrofit? = null
         fun getClient(): Retrofit? {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            val interceptor=HttpLoggingInterceptor();
            interceptor.level= HttpLoggingInterceptor.Level.BODY
            val interceptor2=MyInterceptor()

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(interceptor2)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BasuRl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }

            return retrofit
        }

        fun getPayment(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()

            if (retrofi2 == null) {
                retrofi2 = Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()
            }

            return retrofi2!!
        }

    }
}