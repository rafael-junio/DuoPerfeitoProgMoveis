package com.example.duoperfeito.empresario;

import android.app.Activity;
import android.content.Context;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.duoperfeito.R;
import com.example.duoperfeito.database.dao.VagaDAO;
import com.example.duoperfeito.model.Vaga;

import java.util.List;

public class ListVagasView {
    public final Context context;
    private final VagaDAO database;
    private final ListVagasAdapter adapter;
    Activity act;

    public ListVagasView(Context context, VagaDAO db, Activity act) {
        this.context = context;
        adapter = new ListVagasAdapter(this.context);
        this.database = db;
        this.act = act;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                database.remove(vagaSelecionada);
            }
        }).start();
        adapter.remove(vagaSelecionada);
    }

    public void updateList() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Vaga> vagaList = database.buscaTodos();
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.update(vagaList);
                    }
                });
            }
        }).start();
    }

    public void initializeAdapter(ListView listEquipamentos) {
        listEquipamentos.setAdapter(adapter);
    }

}
