package com.example.duoperfeito.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.duoperfeito.model.Empresario;

@Dao
public interface EmpresarioDAO {

    @Insert
    long salvar(Empresario empresario);

    @Update
    void atualizar(Empresario empresario);

    @Query("SELECT * FROM Empresario")
    List<Empresario> buscarTodos();

    @Query("SELECT * FROM Empresario WHERE id = :id")
    Empresario buscarEmpresario(long id);

    @Query("SELECT * FROM Empresario WHERE email = :email")
    Empresario buscarEmpresarioByEmail(long email);

    @Query("SELECT * FROM Empresario WHERE email = (:email) and senha = :senha")
    Empresario buscarEmpresarioLogin(String email, String senha);

    @Delete
    void remover(Empresario empresario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void salvar(List<Empresario> empresarios);
}
