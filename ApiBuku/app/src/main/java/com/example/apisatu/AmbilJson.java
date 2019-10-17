package com.example.apisatu;
import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface AmbilJson{

    @Headers({
            "X-RapidAPI-Key: 530f6432dfmshfeb2a7e9ae98689p16ded3jsna0bc2e19d368"
    })
    @GET
    Call <List<Pojo>> getDetail(@Url String url);
}
