package com.example.duoperfeito.empresario;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.dao.VagaDAO;
import com.example.duoperfeito.model.Vaga;

public class ListVagasView {
    public final Context context;
    private final VagaDAO database;
    private final ListVagasAdapter adapter;

    public ListVagasView(Context context, VagaDAO db) {
        this.context = context;
        adapter = new ListVagasAdapter(this.context);
        this.database = db;
    }

    public void menuActions(@NonNull final MenuItem item, int menuId) {
        if (menuId == R.id.activity_list_menu_remove) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Vaga vagaSelecionada = adapter.getItem(menuInfo.position);
                new AlertDialog
                        .Builder(context)
                        .setTitle("Remover Vaga")
                        .setMessage("Tem certeza que deseja remover vaga?")
                        .setPositiveButton("Sim", (dialog, which) -> removeItem(vagaSelecionada))
                        .setNegativeButton("Não", null)
                        .show();
            }
    }

    public void removeItem(Vaga vagaSelecionada) {
        database.remove(vagaSelecionada);
        adapter.remove(vagaSelecionada);
    }

    public void updateList() {
        adapter.update(database.buscaTodos());
    }

    public void initializeAdapter(ListView listEquipamentos) {
        listEquipamentos.setAdapter(adapter);
    }

}
