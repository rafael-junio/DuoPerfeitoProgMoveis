package com.example.duoperfeito.profissional;

import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        if (menuId == R.id.activity_list_menu_vaga) {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Vaga vagaSelecionada = adapter.getItem(menuInfo.position);
                new AlertDialog
                        .Builder(context)
                        .setTitle("Vaga")
                        .setMessage("Deseja se candidatar??")
                        .setPositiveButton("Sim", (dialog, which) -> candItem(vagaSelecionada))
                        .setNegativeButton("Não", null)
                        .show();
            }
    }

    public void candItem(Vaga vagaSelecionada) {
        Toast.makeText(context.getApplicationContext(), "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
    }

    public void updateList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter.update(database.buscaTodos());
            }
        }).start();
    }

    public void initializeAdapter(ListView listEquipamentos) {
        listEquipamentos.setAdapter(adapter);
    }

}
