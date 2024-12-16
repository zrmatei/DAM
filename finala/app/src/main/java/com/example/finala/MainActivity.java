package com.example.finala;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finala.classes.Lab;
import com.example.finala.frags.LabFragment;
import com.example.finala.network.AsyncTaskRunner;
import com.example.finala.network.Callback;
import com.example.finala.network.HttpManager;
import com.example.finala.network.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // pt fire de exec : 3 metode
    // metoda 1 - cu thread
    private Button btnShowJSON;
    private static final String URL = "https://api.npoint.io/0bd23b4265753b5c1db1";
    // metoda 2 - cu executor si handler
    // executor rep. practic ce vreau sa citesc (ex: bd, url)
    // handler este metoda / operatia
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    private final Handler handler = new Handler(Looper.getMainLooper());
    // metoda 3 - cu asyncTaskRunner
    private final AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    //pt parsare json
    private Button btnParseJSON;

    //pt lucru cu fragmente (shared pref)
    private Button btnGotoFrag;


    //pt transfer intre activitati
    private ActivityResultLauncher<Intent> launcher;
    private List<Lab> labs = new ArrayList<>();
    private ArrayAdapter<Lab> adapter;
    private ListView listView;
    private Button btnAddCourse;

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

        initComp();
    }

    private void initComp() {
        btnShowJSON = findViewById(R.id.btn_show_json);
        btnParseJSON = findViewById(R.id.btn_parse_json);
        btnGotoFrag = findViewById(R.id.btn_goto_frag);
        btnAddCourse = findViewById(R.id.btn_add_course);
        listView = findViewById(R.id.lv_labs);

        //pas 1 - init launcher + deschidere activitate secundara
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getCallback());
        btnAddCourse.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        });

        //init de adaptor
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, labs);
        listView.setAdapter(adapter);

        btnShowJSON.setOnClickListener(v -> {
//            metoda 1
//            Thread thread = new Thread(() -> {
//                HttpManager httpManager = new HttpManager(URL);
//                String result = httpManager.call();
//                runOnUiThread(getOperationV1(result));
//            });
//            thread.start();

//            metoda 2 - e ok si asta (executor pt run la fir si handler pt fir principal)
//            executorService.execute(() -> {
//                HttpManager httpManager = new HttpManager(URL);
//                String result = httpManager.call();
//                handler.post(getOperation(result));
//            });

//            metoda 3 - cea mai buna (cu async / generics)
            HttpManager httpManager = new HttpManager(URL);
            asyncTaskRunner.asyncTask(httpManager, getOperationOKV2());
        });

        btnParseJSON.setOnClickListener(v -> {
            HttpManager httpManager = new HttpManager(URL);
            asyncTaskRunner.asyncTask(httpManager, getOperationOKV2());

        });

        btnGotoFrag.setOnClickListener(v -> {
            LabFragment fragment = new LabFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container_view, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            if (listView.getCount() != 0) {
                for (int i = 0; i < labs.size(); i++) {
                    labs.remove(i);
                    ArrayAdapter<Lab> adapter = (ArrayAdapter<Lab>) listView.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }
            return true;
        });
    }

    private ActivityResultCallback<ActivityResult> getCallback() {
        //pas 4
        return result -> {
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                //citesc lab
                Lab lab = (Lab) result.getData().getSerializableExtra("list_labs");
                labs.add(lab);
                //notific adaptorul
                ArrayAdapter<Lab> adapter = (ArrayAdapter<Lab>) listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        };
    }

    private Callback<String> getOperationOKV2() {
        return result -> {
            labs.addAll(JsonParser.parser(result));
            Toast.makeText(this, labs.toString(), Toast.LENGTH_SHORT).show();
        };
    }


    private Runnable getOperationV1(String result) {
        return () -> {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        };
    }


}