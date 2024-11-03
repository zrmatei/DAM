package com.example.tema2echipa.classes;

import java.util.Date;

public class dailyTasks {
    private Date zi;
    private String sarcini;

    public Date getZi() {
        return zi;
    }

    public void setZi(Date zi) {
        this.zi = zi;
    }

    public String getSarcini() {
        return sarcini;
    }

    public void setSarcini(String sarcini) {
        this.sarcini = sarcini;
    }

    public dailyTasks(Date zi, String sarcini) {
        this.zi = zi;
        this.sarcini = sarcini;
    }

    @Override
    public String toString() {
        return "dailyTasks{" +
                "zi=" + zi +
                ", sarcini='" + sarcini + '\'' +
                '}';
    }
}
