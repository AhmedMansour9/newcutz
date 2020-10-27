package com.cairocart.Retrofit

import android.content.Context
import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor() :Interceptor {
    lateinit var context:Context

    override fun intercept(chain: Interceptor.Chain): Response {
        var request=chain.request();
        val credentials = "5u1forfnoiuok9qtdaftqxtyd399bcsl"
        val  clientIdclientSecret= Base64.encodeToString(credentials.toByteArray(),Base64.DEFAULT);


        request=request?.newBuilder()


            ?.addHeader("content-type", "application/json")
            ?.addHeader("accept", "application/json")
            ?.addHeader("consumer_key", "ekh2xhe9mzijqcf4ved5c47ny8cz4vp3")
            ?.addHeader("consumer_secret", "b4xx4wve92ltc2wfxq6gpfj6utd22b3j")
//            ?.addHeader("access_token", "Bearer m4ylvans6845xbj5k2qmeq1k8wpn28eq")
            ?.addHeader("Authorization", "Bearer m4ylvans6845xbj5k2qmeq1k8wpn28eq")
//            ?.addHeader("access_token_secret", "dpmvf05buiuoau12ngwxj882v358v8ss")

            ?.build()
        return chain.proceed(request)
    }
}