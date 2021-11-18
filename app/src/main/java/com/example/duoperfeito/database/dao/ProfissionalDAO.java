package com.example.duoperfeito.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duoperfeito.model.Empresario;
import com.example.duoperfeito.model.Profissional;

@Dao
public interface ProfissionalDAO {

    @Insert
    long salvar(Profissional profissional);

    @Update
    void atualiza(Profissional profissional);

    @Query("SELECT * FROM Profissional")
    List<Profissional> buscaTodos();

    @Query("SELECT * FROM Profissional WHERE id = :id")
    Profissional buscaProfissional(long id);

    @Query("SELECT * FROM Profissional WHERE email = :email")
    Profissional buscarProfissionalByEmail(String email);

    @Query("SELECT * FROM Profissional WHERE email = (:email) and senha = :senha")
    Profissional buscarProfissionalLogin(String email, String senha);

    @Delete
    void remove(Profissional profissional);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salvar(List<Profissional> profissionais);
}
