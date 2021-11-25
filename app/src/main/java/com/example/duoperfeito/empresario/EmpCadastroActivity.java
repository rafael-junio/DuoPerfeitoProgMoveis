package com.example.duoperfeito.empresario;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.model.Empresario;

public class EmpCadastroActivity extends AppCompatActivity {

    private EditText nome, telefone, email, cpf, senha;
    private Button cadastrar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_cadastro);
        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);

        startCadastrarButton();
    }

    private void startCadastrarButton() {
        nome = findViewById(R.id.edtTxtNome);
        telefone = findViewById(R.id.edtTxtTelefone);
        email = findViewById(R.id.edtTxtEmail);
        senha = findViewById(R.id.edtTxtSenha);
        cpf = findViewById(R.id.edtTxtCpf);
        cadastrar = findViewById(R.id.btnAtualizar);

        cadastrar.setOnClickListener(view -> {
            Empresario empresarioEntity = new Empresario();
            empresarioEntity.setNome(nome.getText().toString());
            empresarioEntity.setSobrenome(nome.getText().toString());
            empresarioEntity.setEmail(email.getText().toString());
            empresarioEntity.setTelefone(telefone.getText().toString());
            empresarioEntity.setSenha(senha.getText().toString());
            empresarioEntity.setCpf(cpf.getText().toString());

            if (validaInput(empresarioEntity)) {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final EmpresarioDAO empresarioDAO = database.getEmpresarioDAO();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        empresarioDAO.salvar(empresarioEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Empresário cadastrado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        try {
                            editor.putString("token", empresarioEntity.getEmail());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        editor.commit();

                        carregarLogin();
                    }
                }).start();
            }
            else {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Boolean validaInput (Empresario empresarioEntity) {
        return !empresarioEntity.getNome().isEmpty() &&
                !empresarioEntity.getCpf().isEmpty() &&
                !empresarioEntity.getEmail().isEmpty() &&
                !empresarioEntity.getSenha().isEmpty() &&
                !empresarioEntity.getTelefone().isEmpty();
    }

    private void carregarLogin() {
        Intent it = new Intent(EmpCadastroActivity.this, EmpMainActivity.class);
        startActivity(it);
        finish();
    }
}