package com.example.acara10.network;

import com.example.acara10.model.Data;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("3208")
    Call<List<Data>> getAllUsers();
}