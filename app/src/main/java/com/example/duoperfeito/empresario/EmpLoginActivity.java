package com.example.duoperfeito.empresario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.model.Empresario;

public class EmpLoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);
        sharedPreferences = getSharedPreferences("LOGIN", Context.MODE_PRIVATE);
        if (!sharedPreferences.getString("token", "").isEmpty()) {
            carregarLogin();
        }
        startButtons();
    }

    private void startButtons() {
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCadastro = findViewById(R.id.btnAtualizar);

        setLoginButton(btnLogin);
        setCadastroButton(btnCadastro);
    }

    private void setCadastroButton(Button btnCadastro) {
        btnCadastro.setOnClickListener(view -> {
            Intent it = new Intent(EmpLoginActivity.this, EmpCadastroActivity.class);
            startActivity(it);
            finish();
        });
    }

    private void setLoginButton(Button btnLogin) {
        btnLogin.setOnClickListener(view -> {
            email = findViewById(R.id.edtTxtEmail);
            senha = findViewById(R.id.edtTxtSenha);

            String emailTxt = email.getText().toString();
            String senhaTxt = senha.getText().toString();

            if (emailTxt.isEmpty() || senhaTxt.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Preencha corretamente os campos!", Toast.LENGTH_SHORT).show();
            } else {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final EmpresarioDAO empresarioDAO = database.getEmpresarioDAO();
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        Empresario empresarioEntity = empresarioDAO.buscarEmpresarioLogin(emailTxt, senhaTxt);
                        if (empresarioEntity == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {

                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            try {
                                editor.putString("token", empresarioEntity.getEmail());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            editor.commit();

                            carregarLogin();
                        }
                    }
                }).start();
            }
        });
    }

    private void carregarLogin() {
        Intent it = new Intent(EmpLoginActivity.this, EmpMainActivity.class);
        startActivity(it);
        finish();
    }
}