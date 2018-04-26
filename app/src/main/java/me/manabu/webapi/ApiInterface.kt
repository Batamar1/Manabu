package me.manabu.webapi

import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/v1/decks/get")
    fun getAllDecks(): Call<BasicResponse<List<DeckModel>>>

    @GET("/api/v1/deck_to_user/{id}/{userId}")
    fun copyDeckToUser(@Path("id") id: String, @Path("userId") userId: String): Call<ResponseBody>

}