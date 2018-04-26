package me.manabu.webapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {

    private const val BASE_URL: String = "https://manabugora.herokuapp.com"

    val retrofit = configureRetrofit().create(ApiInterface::class.java)!!

    private fun configureRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}