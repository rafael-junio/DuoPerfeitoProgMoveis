package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.duoperfeito.R;
import com.example.duoperfeito.profissional.ProfEditAcivity;
import com.example.duoperfeito.profissional.ProfMainActivity;

public class EmpMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_main);
        String emailTxt = getIntent().getStringExtra("email");

        startButtons(emailTxt);
    }

    private void startButtons(String emailTxt){
        Button btnEmpVagas = findViewById(R.id.btnEmpVagas);
        Button btnEmpCurr = findViewById(R.id.btnEmpCurr);

        btnEmpVagas.setOnClickListener(view -> {
            Intent it = new Intent(EmpMainActivity.this,
                    EmpVagasActivity.class).putExtra("email", emailTxt);
            startActivity(it);
        });

        btnEmpCurr.setOnClickListener(view -> {
            Intent it = new Intent(EmpMainActivity.this,
                    EmpCandActivity.class).putExtra("email", emailTxt);
            startActivity(it);
        });
    }
}