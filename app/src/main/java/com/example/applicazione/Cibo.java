package com.example.applicazione;

public class Cibo {
    int id;
    String nome;
    String categoria;
    Double energia;
    Double lipidi;
    Double acidigrassi;
    Double colesterolo;
    Double carboidrati;
    Double zuccheri;
    Double fibre;
    Double proteine;
    Double sale;

    //TODO: Deve prenderlo dal database
    public Cibo(int id, String nome, String categoria, Double energia, Double lipidi, Double acidigrassi, Double colesterolo, Double carboidrati, Double zuccheri, Double fibre, Double proteine, Double sale) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.energia = energia;
        this.lipidi = lipidi;
        this.acidigrassi = acidigrassi;
        this.colesterolo = colesterolo;
        this.carboidrati = carboidrati;
        this.zuccheri = zuccheri;
        this.fibre = fibre;
        this.proteine = proteine;
        this.sale = sale;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getEnergia() {
        return energia;
    }

    public void setEnergia(Double energia) {
        this.energia = energia;
    }

    public Double getLipidi() {
        return lipidi;
    }

    public void setLipidi(Double lipidi) {
        this.lipidi = lipidi;
    }

    public Double getAcidigrassi() {
        return acidigrassi;
    }

    public void setAcidigrassi(Double acidigrassi) {
        this.acidigrassi = acidigrassi;
    }

    public Double getColesterolo() {
        return colesterolo;
    }

    public void setColesterolo(Double colesterolo) {
        this.colesterolo = colesterolo;
    }

    public Double getCarboidrati() {
        return carboidrati;
    }

    public void setCarboidrati(Double carboidrati) {
        this.carboidrati = carboidrati;
    }

    public Double getZuccheri() {
        return zuccheri;
    }

    public void setZuccheri(Double zuccheri) {
        this.zuccheri = zuccheri;
    }

    public Double getFibre() {
        return fibre;
    }

    public void setFibre(Double fibre) {
        this.fibre = fibre;
    }

    public Double getProteine() {
        return proteine;
    }

    public void setProteine(Double proteine) {
        this.proteine = proteine;
    }

    public Double getSale() {
        return sale;
    }

    public void setSale(Double sale) {
        this.sale = sale;
    }

    @Override
    public String toString() {
        return "Cibo{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoria='" + categoria + '\'' +
                ", energia=" + energia +
                ", lipidi=" + lipidi +
                ", acidigrassi=" + acidigrassi +
                ", colesterolo=" + colesterolo +
                ", carboidrati=" + carboidrati +
                ", zuccheri=" + zuccheri +
                ", fibre=" + fibre +
                ", proteine=" + proteine +
                ", sale=" + sale +
                '}';
    }
}
