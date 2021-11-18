package com.example.duoperfeito.empresario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.repository.EmpresarioRepository;

public class EmpCadastroActivity extends AppCompatActivity {

    EditText nome, telefone, email, cpf, senha;
    Button cadastrar;
    private EmpresarioRepository empresarioRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_cadastro);


        nome = findViewById(R.id.edtTxtNome);
        telefone = findViewById(R.id.edtTxtTelefone);
        email = findViewById(R.id.edtTxtEmail);
        senha = findViewById(R.id.edtTxtSenha);
        cpf = findViewById(R.id.edtTxtCpf);
        cadastrar = findViewById(R.id.btnAtualizar);

        empresarioRepository = new EmpresarioRepository(this);
        cadastrar.setOnClickListener(view -> {
            Empresario empresarioEntity = new Empresario();
            empresarioEntity.setNome(nome.getText().toString());
            empresarioEntity.setSobrenome(nome.getText().toString());
            empresarioEntity.setEmail(email.getText().toString());
            empresarioEntity.setTelefone(telefone.getText().toString());
            empresarioEntity.setSenha(senha.getText().toString());
            empresarioEntity.setCpf(cpf.getText().toString());

            if (validaInput(empresarioEntity)) {
                DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(getApplicationContext());
                final EmpresarioDAO empresarioDAO = database.getEmpresarioDAO();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        empresarioDAO.salvar(empresarioEntity);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Empresário cadastrado!", Toast.LENGTH_SHORT).show();
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
    private Boolean validaInput (Empresario empresarioEntity) {
        return !empresarioEntity.getNome().isEmpty() &&
                !empresarioEntity.getCpf().isEmpty() &&
                !empresarioEntity.getEmail().isEmpty() &&
                !empresarioEntity.getSenha().isEmpty() &&
                !empresarioEntity.getTelefone().isEmpty();
    }

}