package com.example.applicazione.allenamento;

import java.util.ArrayList;
import java.util.List;

public class Allenamento {

    public static Object allenamento;
    private int id;
    private String nome;
    List<Integer> eserciziId;
    List<Integer> eserciziSerie;
    List<Integer> eserciziReps;
    List<Integer> eserciziTrec;
    Integer numElem;

    public Allenamento(int id, String nome, Integer numElem) {
        this.id = id;
        this.nome = nome;
        this.numElem = numElem;
    }

    public Allenamento(String nome, List<Integer> eserciziId, List<Integer> eserciziSerie, List<Integer> eserciziReps, List<Integer> eserciziTrec, Integer numElem) {
        this.nome = nome;
        this.eserciziId = eserciziId;
        this.eserciziSerie = eserciziSerie;
        this.eserciziReps = eserciziReps;
        this.eserciziTrec = eserciziTrec;
        this.numElem = numElem;
    }

    public Allenamento(int id, String nome, List<Integer> eserciziId, List<Integer> eserciziSerie, List<Integer> eserciziReps, List<Integer> eserciziTrec, Integer numElem) {
        this.id = id;
        this.nome = nome;
        this.eserciziId = eserciziId;
        this.eserciziSerie = eserciziSerie;
        this.eserciziReps = eserciziReps;
        this.eserciziTrec = eserciziTrec;
        this.numElem = numElem;
    }

    public static Object getAllenamento() {
        return allenamento;
    }

    public static void setAllenamento(Object allenamento) {
        Allenamento.allenamento = allenamento;
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

    public List<Integer> getEserciziId() {
        return eserciziId;
    }

    public void setEserciziId(List<Integer> eserciziId) {
        this.eserciziId = eserciziId;
    }

    public List<Integer> getEserciziSerie() {
        return eserciziSerie;
    }

    public void setEserciziSerie(List<Integer> eserciziSerie) {
        this.eserciziSerie = eserciziSerie;
    }

    public List<Integer> getEserciziReps() {
        return eserciziReps;
    }

    public void setEserciziReps(List<Integer> eserciziReps) {
        this.eserciziReps = eserciziReps;
    }

    public List<Integer> getEserciziTrec() {
        return eserciziTrec;
    }

    public void setEserciziTrec(List<Integer> eserciziTrec) {
        this.eserciziTrec = eserciziTrec;
    }

    public Integer getNumElem() {
        return numElem;
    }

    public void setNumElem(Integer numElem) {
        this.numElem = numElem;
    }

    @Override
    public String toString() {
        return "Allenamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", eserciziId=" + eserciziId +
                ", eserciziSerie=" + eserciziSerie +
                ", eserciziReps=" + eserciziReps +
                ", eserciziTrec=" + eserciziTrec +
                ", numElem=" + numElem +
                '}';
    }

    public String IdToString(){

        String returnString = eserciziId.get(0).toString();

        for(int i = 1; i < eserciziId.size(); i++) {
            returnString += "," +eserciziId.get(i).toString();

        }

        return returnString;

    }

    public String SerieToString(){

        String returnString = eserciziSerie.get(0).toString();

        for(int i = 1; i < eserciziSerie.size(); i++) {
            returnString += "," +eserciziSerie.get(i).toString();

        }

        return returnString;

    }

    public String RepsToString(){

        String returnString = eserciziReps.get(0).toString();

        for(int i = 1; i < eserciziReps.size(); i++) {
            returnString += "," +eserciziReps.get(i).toString();

        }

        return returnString;

    }

    public String TrecToString(){

        String returnString = eserciziTrec.get(0).toString();

        for(int i = 1; i < eserciziTrec.size(); i++) {
            returnString += "," +eserciziTrec.get(i).toString();

        }

        return returnString;

    }

    public List<Integer> ToArray(String s){

        List<Integer> returnList = new ArrayList<>();

        String[] tokens = s.split(",");

        for(int i = 0; i < tokens.length; i++) {
            returnList.add(Integer.parseInt(tokens[i]));
        }

        return returnList;

    }

    public void incrementNum(){
        numElem++;
    }
}
