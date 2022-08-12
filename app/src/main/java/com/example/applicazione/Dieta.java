package com.example.applicazione;

import java.util.ArrayList;

public class Dieta {
    int id;
    String nome;
    ArrayList<Integer> cibiId;
    ArrayList<Double> cibiQta;
    Integer numElem;

    public Dieta(int id, String nome, /*ArrayList<Integer> cibiId, ArrayList<Double> cibiQta,*/ Integer numElem) {
        this.id = id;
        this.nome = nome;
        /*this.cibiId = cibiId;
        this.cibiQta = cibiQta;*/
        this.numElem = numElem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Integer> getCibiId() {
        return cibiId;
    }

    public void setCibiId(ArrayList<Integer> cibiId) {
        this.cibiId = cibiId;
    }

    public ArrayList<Double> getCibiQta() {
        return cibiQta;
    }

    public void setCibiQta(ArrayList<Double> cibiQta) {
        this.cibiQta = cibiQta;
    }

    public Integer getNumElem() {
        return numElem;
    }

    public void setNumElem(Integer numElem) {
        this.numElem = numElem;
    }

    @Override
    public String toString() {
        return "Dieta{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cibiId=" + cibiId +
                ", cibiQta=" + cibiQta +
                ", numElem=" + numElem +
                '}';
    }
}
