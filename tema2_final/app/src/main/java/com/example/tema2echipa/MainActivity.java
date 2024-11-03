package com.example.tema2echipa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tema2echipa.classes.dailyTasks;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText text_zi;
    private TextInputEditText text_tasks;
    private Button btn_save;
    private Button btn_showList;
    private FloatingActionButton fab_next;
    private FloatingActionButton fabCDTimer;
    private Button btn_about_us;



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
        initComponents();
    }

    private void initComponents(){
        text_zi = findViewById(R.id.tiet_zana_razvan_matei_add_ti_zi);
        text_tasks = findViewById(R.id.tiet_plesanu_florin_eduard_tasks);
        btn_save = findViewById(R.id.zana_razvan_matei_btn_save);
        btn_showList = findViewById(R.id.plesanu_florin_eduard_btn_show_list);
        fab_next = findViewById(R.id.plesanu_florin_eduard_btn_next);
        fabCDTimer=findViewById(R.id.chingaru_george_fab_cdtimer);
        btn_about_us = findViewById(R.id.nina_madalina_eduard_btn_about_us);


        fab_next.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TipsActivity.class);
            startActivity(intent);
        });

        fabCDTimer.setOnClickListener(v->{
            Intent intent=new Intent(getApplicationContext(),CDTimerActivity.class);
            startActivity(intent);
        });

        btn_about_us.setOnClickListener(v ->{
            Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
            startActivity(intent);
        });

        // button save si verificare cu pop-up text + salvare list activitate
        btn_save.setOnClickListener(v -> {
            if (isValid()) {
                dailyTasks dailyTask = null;
                dailyTask = buildDailyTaskFromView();
                String info = "s-a salvat: " + dailyTask.getZi() + " - " + dailyTask.getSarcini();

                // se poate salva in list aici
                saveTasks(new SimpleDateFormat("dd/MM/yyyy", Locale.US).format(dailyTask.getZi()), dailyTask.getSarcini());
                Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                // pop-up message-ul
                Toast.makeText(getApplicationContext(), info, Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
        });

        btn_showList.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ListaActivity.class);
                startActivity(intent);
        });
    }

    private dailyTasks buildDailyTaskFromView() {
        String data = text_zi.getText().toString();
        SimpleDateFormat format_data = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Date zi = null;

        if(!data.isEmpty()) { // adica este cva scris
            try {
                zi = format_data.parse(data);
                String tasks = text_tasks.getText().toString();
                return new dailyTasks(zi, tasks);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }


    private boolean isValid(){
        String data_string = text_zi.getText().toString();

        try {
            if (data_string.isEmpty()) {
                Toast.makeText(getApplicationContext(), R.string.excp_zi, Toast.LENGTH_SHORT).show();
                return false;
            }

            SimpleDateFormat format_data = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            format_data.setLenient(false);
            Date data_parsata = format_data.parse(data_string);

        } catch (ParseException e) {
            Toast.makeText(getApplicationContext(), R.string.excp_zi, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(text_tasks.getText().toString().trim().length() < 1){
            Toast.makeText(getApplicationContext(), R.string.excp_lista_goala, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveTasks(String zi, String sarcini){
        SharedPreferences sp = getSharedPreferences("tasks", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();

        String current_tasks = sp.getString("task_list", "");
        String new_task = "Zi: " + zi + " | Sarcini: " + sarcini;

        String updated_list = current_tasks.isEmpty() ? new_task : current_tasks + "&&" + new_task;

        edit.putString("task_list", updated_list);
        edit.apply();
    }
}