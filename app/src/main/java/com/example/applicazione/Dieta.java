package com.example.applicazione;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Dieta {
    public static Object dieta;
    int id;
    String nome;
    List<Integer> cibiId;
    List<Double> cibiQta;
    Integer numElem;

    public Dieta(String nome, Integer numElem) {
        this.nome = nome;
        this.numElem = numElem;
    }

    public Dieta(int id, String nome, List<Integer> cibiId, List<Double> cibiQta, Integer numElem) {
        this.id = id;
        this.nome = nome;
        this.cibiId = cibiId;
        this.cibiQta = cibiQta;
        this.numElem = numElem;
    }

    public Dieta(int id, String nome, Integer numElem) {
        this.id = id;
        this.nome = nome;
        this.numElem = numElem;
    }

    public Dieta(String nome, List<Integer> cibiId, List<Double> cibiQta, Integer numElem) {
        this.nome = nome;
        this.cibiId = cibiId;
        this.cibiQta = cibiQta;
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

    public List<Integer> getCibiId() {
        return cibiId;
    }

    public void setCibiId(List<Integer> cibiId) {
        this.cibiId = cibiId;
    }

    public List<Double> getCibiQta() {
        return cibiQta;
    }

    public void setCibiQta(List<Double> cibiQta) {
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

    public String IdToString(){

        String returnString = cibiId.get(0).toString();

        for(int i = 1; i < cibiId.size(); i++) {
           returnString += "," +cibiId.get(i).toString();

        }

        return returnString;

    }

    public List<Integer> IdToArray(String s){

        List<Integer> returnList = new ArrayList<>();

        String[] tokens = s.split(",");

        for(int i = 0; i < tokens.length; i++) {
            returnList.add(Integer.parseInt(tokens[i]));
        }

        return returnList;

    }

    public String QtaToString(){

        String returnString = cibiQta.get(0).toString();

        for(int i = 1; i < cibiQta.size(); i++) {
            returnString += "," +cibiQta.get(i).toString();

        }

        return returnString;

    }

    public List<Double> QtaToArray(String s){

        List<Double> returnList = new ArrayList<>();

        String[] tokens = s.split(",");

        for(int i = 0; i < tokens.length; i++) {
            returnList.add(Double.parseDouble(tokens[i]));
        }

        return returnList;

    }

    public void incrementNum(){
        numElem++;
    }
}
