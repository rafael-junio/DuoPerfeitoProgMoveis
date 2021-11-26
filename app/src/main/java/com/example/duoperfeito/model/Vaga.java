package com.example.duoperfeito.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(foreignKeys = @ForeignKey(entity = Empresario.class, parentColumns = "id", childColumns = "empresario_id"))
public class Vaga  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    private String nome;
    private String endereco;
    private String cep;

    @ColumnInfo
    private Long empresario_id;

    public Vaga() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEmpresario_id() {
        return empresario_id;
    }

    public void setEmpresario_id(Long empresario_id) {
        this.empresario_id = empresario_id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
