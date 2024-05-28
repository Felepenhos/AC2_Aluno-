package com.example.ac2.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ac2.R;

import com.example.ac2.api.MockApiService;
import com.example.ac2.models.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListagemAlunosActivity extends AppCompatActivity {

    private ListView listViewAlunos;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem_alunos);

        listViewAlunos = findViewById(R.id.listViewAlunos);

        carregarAlunos();
    }

    private void carregarAlunos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://665503e33c1d3b602938077b.mockapi.io/aluno/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

      MockApiService alunoService = retrofit.create(MockApiService.class);
        Call<List<Aluno>> call = alunoService.listarAlunos ();

        call.enqueue(new Callback<List<Aluno>>() {
            @Override
            public void onResponse(Call<List<Aluno>> call, Response<List<Aluno>> response) {
                if (response.isSuccessful()) {
                    List<Aluno> alunos = response.body();
                    ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(ListagemAlunosActivity.this, android.R.layout.simple_list_item_1, alunos);
                    listViewAlunos.setAdapter(adapter);
                } else {
                    Toast.makeText(ListagemAlunosActivity.this, "Erro ao carregar alunos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Aluno>> call, Throwable t) {
                Toast.makeText(ListagemAlunosActivity.this, "Erro ao carregar alunos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}