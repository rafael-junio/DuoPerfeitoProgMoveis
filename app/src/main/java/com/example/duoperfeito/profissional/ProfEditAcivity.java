package com.example.duoperfeito.profissional;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.ProfissionalDAO;
import com.example.duoperfeito.model.Profissional;

import java.io.ByteArrayOutputStream;

public class ProfEditAcivity extends AppCompatActivity {

    ImageView imgProfile;
    EditText nome, telefone, email, cpf, senha;
    Button atualizar;
    Profissional profissionalEntity;
    byte[] imgByteProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_edit_acivity);

        String emailTxt = getIntent().getStringExtra("email");
        imgProfile = findViewById(R.id.imgProfile);
        atualizar = findViewById(R.id.btnAtualizar);



        DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
        final ProfissionalDAO profissionalDAO = database.getProfissionalDAO();

        new Thread(new Runnable() {
            @Override
            public void run() {
                profissionalEntity = profissionalDAO.buscarProfissionalByEmail(emailTxt);
                setFields();
            }
        }).start();

        imgProfile.setOnClickListener(view -> {
            if (checkPermissions()) {
                capturarCamera();
            }
        });

        atualizar.setOnClickListener(view -> {
            nome = findViewById(R.id.edtTxtNome);
            telefone = findViewById(R.id.edtTxtTelefone);
            email = findViewById(R.id.edtTxtEmail);
            senha = findViewById(R.id.edtTxtSenha);
            cpf = findViewById(R.id.edtTxtCpf);

            profissionalEntity.setNome(nome.getText().toString());
            profissionalEntity.setSobrenome(nome.getText().toString());
            profissionalEntity.setEmail(email.getText().toString());
            profissionalEntity.setTelefone(telefone.getText().toString());
            profissionalEntity.setSenha(senha.getText().toString());
            profissionalEntity.setCpf(cpf.getText().toString());
            if (imgByteProfile != null){
                profissionalEntity.setImage(imgByteProfile);
            }
            if (validaInput(profissionalEntity)){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        profissionalDAO.atualiza(profissionalEntity);
                    }
                }).start();
                Intent it = new Intent(ProfEditAcivity.this,
                        ProfMainActivity.class).putExtra("email", emailTxt);
                startActivity(it);
            }

        });
    }

    private void setFields() {
        nome = findViewById(R.id.edtTxtNome);
        telefone = findViewById(R.id.edtTxtTelefone);
        email = findViewById(R.id.edtTxtEmail);
        senha = findViewById(R.id.edtTxtSenha);
        cpf = findViewById(R.id.edtTxtCpf);
        atualizar = findViewById(R.id.btnAtualizar);

        nome.setText(profissionalEntity.getNome());
        telefone.setText(profissionalEntity.getTelefone());
        email.setText(profissionalEntity.getEmail());
        cpf.setText(profissionalEntity.getCpf());
        senha.setText(profissionalEntity.getSenha());

        if (profissionalEntity.getImage() != null){
            imgProfile.setImageBitmap(convertByteArrayToBitmap(profissionalEntity.getImage()));
        }
    }

    private void capturarCamera() {
        Intent capturaFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (capturaFoto.resolveActivity(getPackageManager()) != null){
            startActivityForResult(capturaFoto, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != 0) {
            Bundle bundle = data.getExtras();
            Bitmap bitmapImg = (Bitmap) bundle.get("data");
            imgByteProfile = convertImageViewToByteArray(bitmapImg);
            imgProfile.setImageBitmap(bitmapImg);
        }


    }
    private Boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int cameraPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if (cameraPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    private byte[] convertImageViewToByteArray(Bitmap bitmapImg){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImg.compress(Bitmap.CompressFormat.JPEG,80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private Bitmap convertByteArrayToBitmap(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private Boolean validaInput (Profissional profissionalEntity) {
        return !profissionalEntity.getNome().isEmpty() &&
                !profissionalEntity.getCpf().isEmpty() &&
                !profissionalEntity.getEmail().isEmpty() &&
                !profissionalEntity.getSenha().isEmpty() &&
                !profissionalEntity.getTelefone().isEmpty();
    }
}