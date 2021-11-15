package com.example.duoperfeito.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Empresario extends Pessoa {

    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    @Ignore
    public Empresario(String nome, String sobrenome, String cpf, String email, String senha, String telefone) {
        super(nome, sobrenome, cpf, email, senha, telefone);
    }

    public void setId(long id) {
        this.id = id;
    }

    public Empresario() {
    }

    public long getId() {
        return id;
    }
}
