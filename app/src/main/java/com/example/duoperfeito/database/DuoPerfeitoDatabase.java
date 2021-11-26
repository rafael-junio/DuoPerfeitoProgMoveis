package com.example.duoperfeito.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.duoperfeito.database.dao.EmpresarioDAO;
import com.example.duoperfeito.database.dao.ProfissionalDAO;
import com.example.duoperfeito.database.dao.VagaDAO;
import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Profissional;
import com.example.duoperfeito.model.Vaga;

@Database(entities = {Empresario.class, Profissional.class, Vaga.class}, version = 1, exportSchema = false)
public abstract class DuoPerfeitoDatabase extends RoomDatabase {

    private static final String NOME_BANCO_DE_DADOS = "duoPerfeito.db";

    public abstract EmpresarioDAO getEmpresarioDAO();
    public abstract ProfissionalDAO getProfissionalDAO();
    public abstract VagaDAO getVagaDAO();

    public static DuoPerfeitoDatabase getInstance(Context context) {
        return Room.databaseBuilder(
                context,
                DuoPerfeitoDatabase.class,
                NOME_BANCO_DE_DADOS)
                .build();
    }
}
