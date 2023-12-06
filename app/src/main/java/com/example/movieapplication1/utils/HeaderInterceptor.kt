package com.example.movieapplication.utils

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        var original: Request = chain.request()
        var builder = chain.request().newBuilder()
        builder.addHeader(
            "Authorization",
            "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzYWUwYjg2OThhNGZkMTc0M2I4ZWI3MGJhZjFkZmVhZSIsInN1YiI6IjY1NmRlNTNiODgwNTUxMDBjNjgyMTViMyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.PrMC3GMJTYbxBdcf9rnI4xvFFAcxmDbKXIpmBPa5kCs"
        )
        builder.addHeader("Content-Type", "application/json;charset=UTF-8")
        builder.addHeader("Accept", "application/vnd.api+json")
        builder.addHeader("Accept-Language", "en")
        return chain.proceed(builder.build())
    }
}