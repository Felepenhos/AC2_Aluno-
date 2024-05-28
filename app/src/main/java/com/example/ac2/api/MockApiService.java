package com.example.ac2.api;


import com.example.ac2.models.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MockApiService {
    @POST("/alunos")
    Call<Aluno> criarAluno(@Body Aluno aluno);

    @GET("/alunos")
    Call<List<Aluno>> listarAlunos();
}