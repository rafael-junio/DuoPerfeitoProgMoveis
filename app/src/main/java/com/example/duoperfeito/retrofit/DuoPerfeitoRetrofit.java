package com.example.duoperfeito.retrofit;

import com.example.duoperfeito.model.Profissional;
import com.example.duoperfeito.retrofit.service.CepService;
import com.example.duoperfeito.retrofit.service.EmpresarioService;
import com.example.duoperfeito.retrofit.service.ProfissionalService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DuoPerfeitoRetrofit {
    private static final String URL_BASE = "https://viacep.com.br/ws/";
    private final CepService cepService;
    private final EmpresarioService empresarioService;
    private final ProfissionalService profissionalService;

    public DuoPerfeitoRetrofit() {
        OkHttpClient client = configuraClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        cepService = retrofit.create (CepService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        empresarioService = retrofit.create (EmpresarioService.class);

         retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        profissionalService = retrofit.create (ProfissionalService.class);
    }

    private OkHttpClient configuraClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public CepService getProdutoService() {
        return cepService;
    }

    public EmpresarioService getEmpresarioService() {
        return empresarioService;
    }

    public ProfissionalService getProfissionalService() {
        return profissionalService;
    }

}
