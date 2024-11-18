package com.example.pregtest.classes;

import java.io.Serializable;
import java.util.Date;

public class Studenti implements Serializable {
    String nume;
    Date data_nastere;
    public Studenti(Date data_nastere, String nume) {
        this.data_nastere = data_nastere;
        this.nume = nume;
    }



    @Override
    public String toString() {
        return "Studenti{" +
                "data_nastere=" + data_nastere +
                ", nume='" + nume + '\'' +
                '}';
    }

    public Date getData_nastere() {
        return data_nastere;
    }

    public void setData_nastere(Date data_nastere) {
        this.data_nastere = data_nastere;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
