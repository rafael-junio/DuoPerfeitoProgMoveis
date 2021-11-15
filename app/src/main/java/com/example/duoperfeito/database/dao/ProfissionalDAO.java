package com.example.duoperfeito.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duoperfeito.model.Profissional;

@Dao
public interface ProfissionalDAO {

    @Insert
    long salva(Profissional profissional);

    @Update
    void atualiza(Profissional profissional);

    @Query("SELECT * FROM Profissional")
    List<Profissional> buscaTodos();

    @Query("SELECT * FROM Profissional WHERE id = :id")
    Profissional buscaProfissional(long id);

    @Delete
    void remove(Profissional profissional);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salva(List<Profissional> profissionais);
}
