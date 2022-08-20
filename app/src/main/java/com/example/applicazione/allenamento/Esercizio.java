package com.example.applicazione.allenamento;

public class Esercizio {

    private int id;
    private String nome;
    private String gruppoMuscolare;
    private String difficolta;
    private String parteDelCorpo;
    private String tipologia;
    private String modalita;

    public Esercizio(int id, String nome, String gruppoMuscolare, String difficolta, String parteDelCorpo, String tipologia, String modalita) {
        this.id = id;
        this.nome = nome;
        this.gruppoMuscolare = gruppoMuscolare;
        this.difficolta = difficolta;
        this.parteDelCorpo = parteDelCorpo;
        this.tipologia = tipologia;
        this.modalita = modalita;
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

    public String getGruppoMuscolare() {
        return gruppoMuscolare;
    }

    public void setGruppoMuscolare(String gruppoMuscolare) {
        this.gruppoMuscolare = gruppoMuscolare;
    }

    public String getDifficolta() {
        return difficolta;
    }

    public void setDifficolta(String difficolta) {
        this.difficolta = difficolta;
    }

    public String getParteDelCorpo() {
        return parteDelCorpo;
    }

    public void setParteDelCorpo(String parteDelCorpo) {
        this.parteDelCorpo = parteDelCorpo;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getModalita() {
        return modalita;
    }

    public void setModalita(String modalita) {
        this.modalita = modalita;
    }
}

