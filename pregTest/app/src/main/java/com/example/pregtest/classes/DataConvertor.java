package com.example.pregtest.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DataConvertor {
    //am nevoie de o variabila simple date format obligatoriu ca sa fac conversiile
    // obligatoriu cele 2 metode sa fie statice!!
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    public static Date toDate(String date){
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toString(Date date){
        if(date == null){
            return null;
        }
        return sdf.format(date);
    }
}
