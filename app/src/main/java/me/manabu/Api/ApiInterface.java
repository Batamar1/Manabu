package me.manabu.Api;

import java.util.List;

import me.manabu.Api.Models.DeckModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @POST("/api/v1/auth/login")
    Call<ResponseBody> login(@Body String gToken);

    @GET("/api/v1/auth/login")
    Call<ResponseBody> getAllDecks(@Body String body);

    @GET("/deck/{id}/lessons")
    Call<List<DeckModel>> getLessonsFromDeck(@Path("id") int id);
}