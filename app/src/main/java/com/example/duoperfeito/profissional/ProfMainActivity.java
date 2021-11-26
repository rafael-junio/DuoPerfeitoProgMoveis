package com.example.duoperfeito.profissional;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.duoperfeito.R;

public class ProfMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_main);

        String emailTxt = getIntent().getStringExtra("email");

        startButtons(emailTxt);
    }

    private void startButtons(String emailTxt){
        Button btnAddInfo = findViewById(R.id.btnAddInfo);
        Button btnStatusInfo = findViewById(R.id.btnStatusInfo);
        Button btnSearchProf = findViewById(R.id.btnSearchProf);

        btnAddInfo.setOnClickListener(view -> {
            Intent it = new Intent(ProfMainActivity.this,
                    ProfEditAcivity.class).putExtra("email", emailTxt);
            startActivity(it);
        });

        btnSearchProf.setOnClickListener(view -> {
            Intent it = new Intent(ProfMainActivity.this,
                    ProfVagasActivity.class).putExtra("email", emailTxt);
            startActivity(it);
        });
    }
}