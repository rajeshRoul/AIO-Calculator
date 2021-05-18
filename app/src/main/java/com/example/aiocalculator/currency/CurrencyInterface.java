package com.example.aiocalculator.currency;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CurrencyInterface {
    @GET("v6/dd76ba72b4c331fbf68d7207/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
