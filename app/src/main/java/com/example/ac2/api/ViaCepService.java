package com.example.ac2.api;

import com.example.ac2.models.Aluno;
import com.example.ac2.models.Endereco;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ViaCepService {
    @GET("{cep}/json/")
    Call<Endereco> buscarEndereco(@Path("cep") String cep);

    public class RetrofitClient {
        private static Retrofit retrofit;
        private static final String BASE_URL = "https://viacep.com.br/";

        public static Retrofit getRetrofitInstance() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

    }
}
