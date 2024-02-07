package com.miklegol.numbersfacts.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {
    @GET("random/math")
    fun getRandomFact(): Call<String>

    @GET("{number}")
    fun getFact(@Path("number") number: String): Call<String>
}