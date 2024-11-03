package com.example.tema2echipa;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tema2echipa.classes.ListaAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // pt transfer main-list activity
        SharedPreferences sp = getSharedPreferences("tasks", MODE_PRIVATE);
        String task_string = sp.getString("task_list", "");

        List<String> task_data;

        if (!task_string.isEmpty()) {
            task_data = new ArrayList<>(Arrays.asList(task_string.split("&&")));
        }else {
            task_data = new ArrayList<>();
            task_data.add(getString(R.string.excp_lista_goala));
        }

        // pt actualizare lista dupa cd
        if(getIntent().getBooleanExtra("remove_item", false)){
            task_data.remove(0);
            SharedPreferences.Editor editor = sp.edit();
            String updated_list = String.join("&&", task_data);
            editor.putString("task_list", updated_list);
            editor.apply();
        }

        ListaAdapter adapter = new ListaAdapter(task_data);
        recyclerView.setAdapter(adapter);
    }
}