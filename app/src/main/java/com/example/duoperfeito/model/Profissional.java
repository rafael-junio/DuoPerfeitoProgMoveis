package com.example.duoperfeito.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Profissional extends Pessoa{

    @PrimaryKey(autoGenerate = true)
    private long id = 0;
    private boolean disponivel = false;
    private String cargo = null;
    private String curriculo = null;

    @Ignore
    public Profissional(String nome, String sobrenome, String cpf, String email, String senha, String telefone, boolean disponivel, String cargo, String curriculo) {
        super(nome, sobrenome, cpf, email, senha, telefone);
        this.disponivel = disponivel;
        this.cargo = cargo;
        this.curriculo = curriculo;
    }

    public Profissional() {

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setCurriculo(String curriculo) {
        this.curriculo = curriculo;
    }

    public long getId() {
        return id;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCurriculo() {
        return curriculo;
    }
}
