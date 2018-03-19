package me.manabu.webapi

import me.manabu.webapi.models.DeckModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {

    @POST("/api/v1/auth/login")
    fun login(@Body gToken: String): Call<ResponseBody>

    @GET("/api/v1/auth/login")
    fun getAllDecks(@Body body: String): Call<ResponseBody>

    @GET("/deck/{id}/lessons")
    fun getLessonsFromDeck(@Path("id") id: Int): Call<List<DeckModel>>
}