package com.example.duoperfeito.empresario;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.DuoPerfeitoDatabase;
import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.database.dao.VagaDAO;
import com.example.duoperfeito.model.Vaga;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmpVagasActivity extends AppCompatActivity {

    public static final String TITLE_APP = "Vagas";
    private ListVagasView listVagasView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emp_vagas);
        setTitle(TITLE_APP);

        DuoPerfeitoDatabase database = DuoPerfeitoDatabase.getInstance(this);
        VagaDAO vagaDAO = database.getVagaDAO();
        listVagasView = new ListVagasView(this, vagaDAO);
        configureBtns();
        initializeList();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_list_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull final MenuItem item) {
        int menuId = item.getItemId();
        listVagasView.menuActions(item, menuId);
        return super.onContextItemSelected(item);
    }

    private void configureBtns() {
        FloatingActionButton btnNewEquipamento = findViewById(R.id.fabAddVagas);
        btnNewEquipamento.setOnClickListener(v -> startActivity(new Intent(EmpVagasActivity.this, EmpVagasCadastroActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        listVagasView.updateList();
    }

    private void initializeList() {
        ListView listEquipamentos = findViewById(R.id.activity_listview_equipamentos);
        listVagasView.initializeAdapter(listEquipamentos);
        editListItem(listEquipamentos);
        registerForContextMenu(listEquipamentos);
    }

    private void editListItem(ListView listEquipamentos) {
        listEquipamentos.setOnItemClickListener((parent, view, position, id) -> {
            Vaga vagaSelecionada = (Vaga) parent.getItemAtPosition(position);
            Intent goToActivity = new Intent(EmpVagasActivity.this, EmpVagasCadastroActivity.class);
//            goToActivity.putExtra("vaga", vagaSelecionada);
            startActivity(goToActivity);
        });
    }
}