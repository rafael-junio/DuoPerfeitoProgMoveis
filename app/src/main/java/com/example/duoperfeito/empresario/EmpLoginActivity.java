package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.duoperfeito.MainActivity;
import com.example.duoperfeito.R;
import com.example.duoperfeito.profissional.ProfLoginActivity;

public class EmpLoginActivity extends AppCompatActivity {

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
            Intent it = new Intent(EmpLoginActivity.this, EmpMainActivity.class);
            startActivity(it);
        });
        btnCadastro.setOnClickListener(view -> {
            Intent it = new Intent(EmpLoginActivity.this, EmpCadastroActivity.class);
            startActivity(it);
        });
    }
}