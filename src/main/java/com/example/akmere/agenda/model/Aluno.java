package com.example.akmere.agenda.model;

import java.io.Serializable;

/**
 * Created by akmere on 12/25/16.
 */
public class Aluno implements Serializable{
    private String nome;
    private String sexo;
    private String nascimento;
    private String situacao;
    private Long id;
    private String site;
    private String phone;
    private String address;



    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString()  {
        return getId() + " - " + getNascimento() + " - " + getNome() + " - " + getSexo() + " - " + getSituacao();
    }




}
