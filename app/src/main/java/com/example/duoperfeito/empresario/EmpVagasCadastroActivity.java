package com.example.duoperfeito.empresario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Vaga;

public class EmpVagasCadastroActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DuoPerfeitoDatabase database;

    private EditText nome, endereco, cep;
    private Button cadastrarVaga;
    private Vaga vaga;
    private Empresario empresario;
    private boolean novaVaga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_vagas_cadastro);
        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        database = DuoPerfeitoDatabase.getInstance(this);

        inicializeFields();
        initializeForm();

        startCadastrarButton();
    }

    private void inicializeFields() {
        nome = findViewById(R.id.edtTxtNome);
        endereco = findViewById(R.id.edtTxtEndereco);
        cep = findViewById(R.id.edtTxtCep);
        cadastrarVaga = findViewById(R.id.btnCadastrarVaga);
    }

    private void initializeForm() {
        Intent dataCurrent = getIntent();

        if (dataCurrent.hasExtra("vaga")) {
            vaga = (Vaga) dataCurrent.getSerializableExtra("vaga");
            nome.setText(vaga.getNome());
            endereco.setText(vaga.getEndereco());
            cep.setText(vaga.getCep());
            novaVaga = false;
        } else {
            vaga = new Vaga();
            novaVaga = true;
        }
    }

    private void finalizarFormulario() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (novaVaga) {
                    database.getVagaDAO().salvar(vaga);
                } else {
                    database.getVagaDAO().atualiza(vaga);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Vaga cadastrado!", Toast.LENGTH_SHORT).show();
                    }
                });
                finish();
            }
        }).start();
    }

    private void criaVaga() {
        String n = nome.getText().toString();
        vaga.setNome(n);

        String e = endereco.getText().toString();
        vaga.setEndereco(e);

        String c = cep.getText().toString();
        vaga.setCep(c);

        if (empresario != null) {
            vaga.setEmpresario_id(empresario.getId());
        }
    }

    private void buscaEmpresario() {
        String empresarioEmail = sharedPreferences.getString("token", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                empresario = database.getEmpresarioDAO().buscarEmpresarioByEmail(empresarioEmail);
            }
        }).start();
    }

    private void startCadastrarButton() {
        cadastrarVaga.setOnClickListener(view -> {
            buscaEmpresario();
            criaVaga();
            if (validaInput(vaga)) {
                finalizarFormulario();
            } else {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validaInput(Vaga vagasEntity) {
        return !vagasEntity.getNome().isEmpty() &&
                !vagasEntity.getEndereco().isEmpty() &&
                !vagasEntity.getCep().isEmpty();
    }
}