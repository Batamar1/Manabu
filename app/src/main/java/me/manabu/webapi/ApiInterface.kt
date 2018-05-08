package me.manabu.webapi

import me.manabu.webapi.models.BasicResponse
import me.manabu.webapi.models.DeckLevelModel
import me.manabu.webapi.models.DeckModel
import me.manabu.webapi.models.UserDeckModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/api/v1/decks/get")
    fun getAllDecks(): Call<BasicResponse<List<DeckModel>>>

    @GET("/api/v1/deck_to_user/{deckId}/{userId}")
    fun copyDeckToUser(@Path("deckId") deckId: String, @Path("userId") userId: String): Call<ResponseBody>

    @GET("/api/v1/user_deck/delete/{deckId}")
    fun removeUserDeck(@Path("deckId") deckId: String): Call<ResponseBody>

    @GET("/api/v1/user_decks/get/{userId}")
    fun getUserDecks(@Path("userId") userId: String): Call<BasicResponse<List<UserDeckModel>>>

    @GET("/api/v1/deck/{deckId}/lessons")
    fun getAvailableLessons(@Path("deckId") deckId: String): Call<BasicResponse<List<DeckLevelModel>>>

}