package com.example.duoperfeito.profissional;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.database.dao.ProfissionalDAO;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Profissional;
import com.example.duoperfeito.repository.EmpresarioRepository;
import com.example.duoperfeito.repository.ProfissionalRepository;

public class ProfCadastroActivity extends AppCompatActivity {

    EditText nome, telefone, email, cpf, senha;
    Button cadastrar;
    private ProfissionalRepository profissionalRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_cadastro);

        nome = findViewById(R.id.edtTxtNome);
        telefone = findViewById(R.id.edtTxtTelefone);
        email = findViewById(R.id.edtTxtEmail);
        senha = findViewById(R.id.edtTxtSenha);
        cpf = findViewById(R.id.edtTxtCpf);
        cadastrar = findViewById(R.id.btnCadastro);

        profissionalRepository = new ProfissionalRepository(this);
        cadastrar.setOnClickListener(view -> {
            Profissional profissionalEntity = new Profissional();
            profissionalEntity.setNome(nome.getText().toString());
            profissionalEntity.setSobrenome(nome.getText().toString());
            profissionalEntity.setEmail(email.getText().toString());
            profissionalEntity.setTelefone(telefone.getText().toString());
            profissionalEntity.setSenha(senha.getText().toString());
            profissionalEntity.setCpf(cpf.getText().toString());

            if (validaInput(profissionalEntity)) {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final ProfissionalDAO profissionalDao = database.getProfissionalDAO();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        profissionalDao.salvar(profissionalEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Profissional cadastrado!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
            }
            else {
                Toast.makeText(getApplicationContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            }
//            empresarioRepository.salva(empresario, new EmpresarioRepository.DadosCarregadosCallback<Empresario>() {
//                @Override
//                public void quandoSucesso(Empresario resultado) {
//                    Toast.makeText(getApplicationContext(), "Empresário cadastrado!", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void quandoFalha(String erro) {
//                    Toast.makeText(getApplicationContext(), "Preencha corretamente os campos!", Toast.LENGTH_SHORT).show();
//                }
//            });
        });
    }
    private Boolean validaInput (Profissional profissionalEntity) {
        return !profissionalEntity.getNome().isEmpty() &&
                !profissionalEntity.getCpf().isEmpty() &&
                !profissionalEntity.getEmail().isEmpty() &&
                !profissionalEntity.getSenha().isEmpty() &&
                !profissionalEntity.getTelefone().isEmpty();
    }
}