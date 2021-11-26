package com.example.duoperfeito.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Empresario.class, parentColumns = "id", childColumns = "empresario_id"),
        @ForeignKey(entity = Profissional.class, parentColumns = "id", childColumns = "profissional_id")
})
public class Vaga {

    @PrimaryKey(autoGenerate = true)
    private long id = 0;

    private String nome;
    private Endereco endereco;
    private String cep;

    @ColumnInfo
    private Long empresario_id;

    @ColumnInfo
    private Long profissional_id;

    @Ignore
    public Vaga(long id, String nome, Long empresario_id, Long profissional_id, Endereco endereco, String cep) {
        this.id = id;
        this.nome = nome;
        this.empresario_id = empresario_id;
        this.profissional_id = profissional_id;
        this.endereco = endereco;
        this.cep = cep;
    }

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

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Long getEmpresario_id() {
        return empresario_id;
    }

    public void setEmpresario_id(Long empresario_id) {
        this.empresario_id = empresario_id;
    }

    public Long getProfissional_id() {
        return profissional_id;
    }

    public void setProfissional_id(Long profissional_id) {
        this.profissional_id = profissional_id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
