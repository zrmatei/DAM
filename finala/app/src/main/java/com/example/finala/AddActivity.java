package com.example.finala;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finala.classes.Lab;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private TextInputEditText tietID, tietName, tietNumber;
    private Button btnSave;
    private Intent intent;
    private List<Lab> labs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //pas 2 - preiau intentul
        intent = getIntent();

        initComp();
    }

    private void initComp() {
        tietID = findViewById(R.id.tiet_id_main);
        tietName = findViewById(R.id.tiet_name_main);
        tietNumber = findViewById(R.id.tiet_number_main);
        btnSave = findViewById(R.id.btn_save_to_main);



        btnSave.setOnClickListener(v -> {
            //pas 3 - trimit inapoi prin atasare
            if(isValid()){
                int id = Integer.parseInt(tietID.getText().toString());
                String name = tietName.getText().toString();
                int number = Integer.parseInt(tietNumber.getText().toString());
                Lab lab = new Lab(id, name, number);

                //atasez intentul
                intent.putExtra("list_labs", lab);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        return true;
    }
}