package com.example.duoperfeito.retrofit.service;

import com.example.duoperfeito.model.Empresario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmpresarioService {
    @GET("empresario")
    Call<List<Empresario>> buscaTodos();

    @POST("empresario")
    Call<Empresario> salva(@Body Empresario empresario);

    @PUT("empresario/{id}")
    Call<Empresario> edita(@Path("id") long id,
                        @Body Empresario empresario);

    @DELETE("empresario/{id}")
    Call<Void> remove(@Path("id") long id);
}
