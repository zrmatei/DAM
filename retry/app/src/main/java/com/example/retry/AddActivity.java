package com.example.retry;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retry.classes.Course;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private Intent intent;
    private TextInputEditText tietID, tietName, tietNumber;
    private Button btnSave;

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

        initComp();
        //pas 2 - trimit inf inapoi in main
        intent = getIntent();

    }


    private void initComp() {
        tietID = findViewById(R.id.tiet_id_main);
        tietName = findViewById(R.id.tiet_name_main);
        tietNumber = findViewById(R.id.tiet_number_main);
        btnSave = findViewById(R.id.btn_save_to_main);


        btnSave.setOnClickListener(v -> {
            if(isValid()){
                int id = Integer.parseInt(tietID.getText().toString());
                String name = tietName.getText().toString();
                int number = Integer.parseInt(tietNumber.getText().toString());
                Course course =  new Course(id, name, number);

                //pasul 3 - atasare intent cu inf date
                //altfel primesc eroare la partea asta
                intent.putExtra("list_course", course);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private boolean isValid() {
        return true;
    }
}