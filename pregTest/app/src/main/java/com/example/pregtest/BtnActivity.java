package com.example.pregtest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pregtest.classes.DataConvertor;
import com.example.pregtest.classes.Studenti;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class BtnActivity extends AppCompatActivity {
    private TextInputEditText tiet_nume;
    private TextInputEditText tiet_data;
    private Button btn_save;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_btn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initComponents();

        //pas 2 - iau ceea ce am scris in intent (daca folosesc new se taie legatura)
        intent = getIntent();
    }

    private void initComponents() {
        tiet_nume = findViewById(R.id.main_test_text);
        tiet_data = findViewById(R.id.main_test2_text);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(v -> {
            if (isValid()){

                //creare obiect
                Studenti student = buildFromView();

                //pas 3 - pe intent atasez datele / trb sa pun serializable la cls student pt ca
                //intent sa mearga
                intent.putExtra("stud_key", student);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    //citesc in variabile Java toate componentele mele din activity folosind metoda asta:
    //practic metoda pentru creare obiect (constructor)
    private Studenti buildFromView() {
        String nume = tiet_nume.getText().toString();
        Date date = DataConvertor.toDate(tiet_data.getText().toString());
        return new Studenti(date, nume);
    }

    private boolean isValid(){
        if(tiet_nume.getText().toString().trim().length() < 3){
            Toast.makeText(getApplicationContext(), "Minim 3 caractere", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(tiet_data.getText() == null || DataConvertor.toDate(tiet_data.getText().toString()) == null){
            Toast.makeText(getApplicationContext(), "Invalid format", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}