package com.miklegol.numbersfacts.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitInstance {

    val api: NumbersApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://numbersapi.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(NumbersApi::class.java)
    }
}
