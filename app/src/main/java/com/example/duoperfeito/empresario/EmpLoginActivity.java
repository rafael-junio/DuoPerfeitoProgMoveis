package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.MainActivity;
import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.profissional.ProfLoginActivity;
import com.example.duoperfeito.repository.EmpresarioRepository;

public class EmpLoginActivity extends AppCompatActivity {
    EditText email, senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        startButtons();
    }

    private void startButtons() {
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnCadastro = findViewById(R.id.btnCadastro);

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
                final EmpresarioDAO empresarioDAO = database.getEmpresarioDAO();
                new Thread(new Runnable() {
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
                        }
                        else {
                            Intent it = new Intent(EmpLoginActivity.this, EmpMainActivity.class);
                            startActivity(it);
                        }
                    }
                }).start();
            }

        });
        btnCadastro.setOnClickListener(view -> {
            Intent it = new Intent(EmpLoginActivity.this, EmpCadastroActivity.class);
            startActivity(it);
        });
    }
}