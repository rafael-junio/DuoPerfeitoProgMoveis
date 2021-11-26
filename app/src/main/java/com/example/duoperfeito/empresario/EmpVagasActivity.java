package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;

import com.example.duoperfeito.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmpVagasActivity extends AppCompatActivity {

    FloatingActionButton fabAddVagas;
    private String emailTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_vagas);

        emailTxt = getIntent().getStringExtra("email");

        startFab();
    }

    public void startFab(){
        fabAddVagas = findViewById(R.id.fabAddVagas);

        fabAddVagas.setOnClickListener(view -> {
            Intent it = new Intent(EmpVagasActivity.this,
                    EmpVagasCadastroActivity.class).putExtra("email", emailTxt);
            startActivity(it);
        });
    }
}