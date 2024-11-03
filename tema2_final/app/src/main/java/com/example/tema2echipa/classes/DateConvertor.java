package com.example.tema2echipa.classes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateConvertor {
    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    public static Date toDateConvert(String text)  {
        try {
            return formatter.parse(text);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String fromDateConvert(Date date){
        if(date == null){
            return null;
        }
        return formatter.format(date);
    }
}
