package com.example.applicazione;

public class Esercizio {

    private String nome;        //nome esercizio
    private String id;          //id esercizio

    private int serie;          //numero serie
    private int reps;           //ripetizioni per serie
    private double tserie;      //tempo di esecuzione di una serie
    private double rec;         //tempo di recupero tra una serie e l'altra


    public Esercizio(String nome, String id, int serie, int reps, double tserie, double rec) {
        this.nome = nome;
        this.id = id;
        this.serie = serie;
        this.reps = reps;
        this.tserie = tserie;
        this.rec = rec;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getTserie() {
        return tserie;
    }

    public void setTserie(double tserie) {
        this.tserie = tserie;
    }

    public double getRec() {
        return rec;
    }

    public void setRec(double rec) {
        this.rec = rec;
    }
}

