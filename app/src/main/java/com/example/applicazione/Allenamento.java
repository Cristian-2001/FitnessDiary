package com.example.applicazione;

public class Allenamento {
    private int id;
    private String nome;
    private double kcal;
    public String getImageUrl;

    public Allenamento(int id, String nome, double kcal, String getImageUrl) {
        this.id = id;
        this.nome = nome;
        this.kcal = kcal;
        this.getImageUrl = getImageUrl;
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

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public String getGetImageUrl() {
        return getImageUrl;
    }

    public void setGetImageUrl(String getImageUrl) {
        this.getImageUrl = getImageUrl;
    }

    @Override
    public String toString() {
        return "Allenamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", kcal=" + kcal +
                ", getImageUrl='" + getImageUrl + '\'' +
                '}';
    }
}
