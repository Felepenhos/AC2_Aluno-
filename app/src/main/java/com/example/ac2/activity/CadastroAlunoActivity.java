package com.example.ac2.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ac2.R;
import com.example.ac2.api.MockApiService;
import com.example.ac2.api.ViaCepService;
import com.example.ac2.models.Aluno;
import com.example.ac2.models.Endereco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroAlunoActivity extends AppCompatActivity {

    private EditText editTextRa, editTextNome, editTextCep, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUf;
    private Button buttonBuscarCep, buttonSalvar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ac2.R.layout.activity_cadastro_aluno);

        editTextRa = findViewById(R.id.editTextRa);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCep = findViewById(R.id.editTextCep);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUf = findViewById(R.id.editTextUf);

        buttonBuscarCep = findViewById(R.id.buttonBuscarCep);
        buttonSalvar = findViewById(R.id.buttonSalvar);

        buttonBuscarCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCep();
            }
        });

        buttonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });
    }

    private void buscarCep() {
        String cep = editTextCep.getText().toString();
        Retrofit retrofit = ViaCepService.RetrofitClient.getRetrofitInstance();
        ViaCepService viaCepService = retrofit.create(ViaCepService.class);
        Call<Endereco> call = viaCepService.buscarEndereco(cep);

        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                if (response.isSuccessful()) {
                    Endereco endereco = response.body();
                    if (endereco != null) {
                        editTextLogradouro.setText(endereco.getLogradouro());
                        editTextComplemento.setText(endereco.getComplemento());
                        editTextBairro.setText(endereco.getBairro());
                        editTextCidade.setText(endereco.getLocalidade());
                        editTextUf.setText(endereco.getUf());
                    } else {
                        Toast.makeText(CadastroAlunoActivity.this, "CEP não encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void salvarAluno() {
        int ra = Integer.parseInt(editTextRa.getText().toString());
        String nome = editTextNome.getText().toString();
        String cep = editTextCep.getText().toString();
        String logradouro = editTextLogradouro.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String uf = editTextUf.getText().toString();

        Aluno aluno = new Aluno(ra, nome, cep, logradouro, complemento, bairro, cidade, uf);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://665503e33c1d3b602938077b.mockapi.io/aluno/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

      MockApiService alunoService = retrofit.create(MockApiService.class);
        Call<Aluno> call = alunoService.criarAluno(aluno);

        call.enqueue(new Callback<Aluno>() {
            @Override
            public void onResponse(Call<Aluno> call, Response<Aluno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroAlunoActivity.this, "Aluno salvo com sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Aluno> call, Throwable t) {
                Toast.makeText(CadastroAlunoActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

