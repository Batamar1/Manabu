package me.manabu.utils;

import me.manabu.webapi.ApiInterface;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final String BASE_URL = "https://manabugora.herokuapp.com";
    private static final String BASE_URL_SLASH = BASE_URL + "/";

    public static final ApiInterface retrofit = configureRetrofit().create(ApiInterface.class);

    private static Retrofit configureRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_SLASH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}