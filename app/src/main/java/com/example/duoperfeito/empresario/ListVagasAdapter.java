package com.example.duoperfeito.empresario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.duoperfeito.R;
import com.example.duoperfeito.model.Vaga;

import java.util.ArrayList;
import java.util.List;

public class ListVagasAdapter extends BaseAdapter {
    private final List<Vaga> vagas = new ArrayList<>();
    private final Context context;

    public ListVagasAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return vagas.size();
    }

    @Override
    public Vaga getItem(int position) {
        return vagas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return vagas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View newView = LayoutInflater.from(context).inflate(R.layout.item_vaga, parent, false);
        Vaga vaga = vagas.get(position);

        TextView nome = newView.findViewById(R.id.item_vaga_nome);
        nome.setText(vaga.getNome());

        return newView;
    }

    private void clear() {
        vagas.clear();
    }

    private void addAll(List<Vaga> vagas) {
        this.vagas.addAll(vagas);
    }

    public void update(List<Vaga> vagas) {
        clear();
        addAll(vagas);
        notifyDataSetChanged();
    }

    public void remove(Vaga vaga) {
        vagas.remove(vaga);
        notifyDataSetChanged();
    }
}
