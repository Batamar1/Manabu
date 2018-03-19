package me.manabu.utils

import me.manabu.webapi.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitUtils {

    private val BASE_URL = "https://manabugora.herokuapp.com"
    private val BASE_URL_SLASH = BASE_URL + "/"

    val retrofit = configureRetrofit().create(ApiInterface::class.java)

    private fun configureRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL_SLASH)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}