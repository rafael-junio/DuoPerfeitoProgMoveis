package com.example.duoperfeito.retrofit.service;

import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Profissional;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProfissionalService {
    @GET("profissional")
    Call<List<Profissional>> buscaTodos();

    @POST("profissional")
    Call<Profissional> salva(@Body Profissional profissional);

    @PUT("profissional/{id}")
    Call<Profissional> edita(@Path("id") long id,
                           @Body Profissional profissional);

    @DELETE("profissional/{id}")
    Call<Void> remove(@Path("id") long id);
}
