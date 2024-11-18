package com.example.pregtest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
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

import com.example.pregtest.classes.Studenti;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private ActivityResultLauncher<Intent> launcher;
    private ListView lvStud;
    private List<Studenti> listStud = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fab = findViewById(R.id.main_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), BtnActivity.class);
            //startActivity(intent);

            //pasul 1 - lansez lansatorul
            launcher.launch(intent);
        });

        lvStud = findViewById(R.id.main_lv_stud);
        ArrayAdapter<Studenti> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listStud);
        lvStud.setAdapter(adapter);

        //initializare launcher in onCreate
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), resultcallback());
    }

    private ActivityResultCallback<ActivityResult> resultcallback() {
        return r -> {
            if(r.getResultCode() == RESULT_OK && r.getData() != null){
                Studenti student = (Studenti) r.getData().getSerializableExtra("stud_key");
                listStud.add(student);

                ArrayAdapter<Studenti> adapter = (ArrayAdapter<Studenti>) lvStud.getAdapter();
                adapter.notifyDataSetChanged();
            }
        };
    }
}