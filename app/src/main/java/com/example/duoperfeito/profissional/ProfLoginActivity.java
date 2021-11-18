package com.example.duoperfeito.profissional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.ProfissionalDAO;
import com.example.duoperfeito.model.Profissional;

public class ProfLoginActivity extends AppCompatActivity {

    EditText email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_login);

        startButtons();
    }

    private void startButtons() {
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCadastro = findViewById(R.id.btnAtualizar);

        btnLogin.setOnClickListener(view -> {
            email = findViewById(R.id.edtTxtEmail);
            senha = findViewById(R.id.edtTxtSenha);

            String emailTxt = email.getText().toString();
            String senhaTxt = senha.getText().toString();

            if (emailTxt.isEmpty() || senhaTxt.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Preencha corretamente os campos!", Toast.LENGTH_SHORT).show();
            }
            else {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final ProfissionalDAO profissionalDAO = database.getProfissionalDAO();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Profissional profissionalEntity = profissionalDAO.buscarProfissionalLogin(emailTxt, senhaTxt);
                        if (profissionalEntity == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Credenciais inválidas", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else {
                            Intent it = new Intent(ProfLoginActivity.this,
                                    ProfMainActivity.class).putExtra("email", emailTxt);
                            startActivity(it);
                        }
                    }
                }).start();
            }

        });
        btnCadastro.setOnClickListener(view -> {
            Intent it = new Intent(ProfLoginActivity.this, ProfCadastroActivity.class);
            startActivity(it);
        });
    }
}