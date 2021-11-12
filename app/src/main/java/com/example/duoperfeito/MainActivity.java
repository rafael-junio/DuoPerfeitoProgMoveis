package com.example.duoperfeito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.duoperfeito.empresario.EmpLoginActivity;
import com.example.duoperfeito.profissional.ProfLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButtons();
    }

//    TODO: Da para fazer usando uma única tela de login, mas para facilitar fiz duas mesmo
    private void startButtons() {
        Button btnEmp = findViewById(R.id.btnEmp);
        Button btnProf = findViewById(R.id.btnProf);

        btnEmp.setOnClickListener(view -> {
            Intent it = new Intent(MainActivity.this, EmpLoginActivity.class);
            startActivity(it);
        });
        btnProf.setOnClickListener(view -> {
            Intent it = new Intent(MainActivity.this, ProfLoginActivity.class);
            startActivity(it);
        });
    }
}