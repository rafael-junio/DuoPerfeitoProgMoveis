package com.example.duoperfeito.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Profissional;
import com.example.duoperfeito.model.Vaga;

import java.util.List;

@Dao
public interface VagaDAO {

    @Insert
    long salvar(Vaga vaga);

    @Update
    void atualiza(Vaga vaga);

    @Query("SELECT * FROM Vaga")
    List<Vaga> buscaTodos();

    @Query("SELECT * FROM Vaga WHERE id = :id")
    Vaga buscaVaga(long id);

    @Query("SELECT * FROM Vaga WHERE empresario_id = :empresario_id")
    Empresario buscaEmpresario(Long empresario_id);

    @Delete
    void remove(Vaga vaga);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salvar(List<Vaga> vagas);
}
