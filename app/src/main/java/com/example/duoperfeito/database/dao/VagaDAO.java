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
    long salvar(Profissional profissional);

    @Update
    void atualiza(Profissional profissional);

    @Query("SELECT * FROM Vaga")
    List<Profissional> buscaTodos();

    @Query("SELECT * FROM Vaga WHERE id = :id")
    Profissional buscaProfissional(long id);

    @Query("SELECT * FROM Vaga WHERE profissional_id = :profissional_id")
    Profissional buscarProfissional(Long profissional_id);

    @Query("SELECT * FROM Vaga WHERE empresario_id = :empresario_id")
    Empresario buscarEmpresario(Long empresario_id);

    @Delete
    void remove(Vaga vaga);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salvar(List<Vaga> vagas);
}
