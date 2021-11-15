package com.example.duoperfeito.retrofit.service;

import com.example.duoperfeito.model.Endereco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {

    /* Retorna uma lista de objetos CEP */
    @GET("{estado}/{cidade}/{endereco}/json/")
    Call<List<Endereco>> getCEPByCidadeEstadoEndereco(@Path("estado") String estado,
                                                      @Path("cidade") String cidade,
                                                      @Path("endereco") String endereco);

    /* Retorna apenas um objeto CEP */
    @GET("{CEP}/json/")
    Call<Endereco> getEnderecoByCEP(@Path("CEP") String CEP);

}
