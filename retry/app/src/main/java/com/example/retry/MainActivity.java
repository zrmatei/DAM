package com.example.retry;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.retry.classes.Course;
import com.example.retry.classes.CourseAdapter;
import com.example.retry.fragments.HomeFragment;
import com.example.retry.network.AsyncTaskRun;
import com.example.retry.network.Callback;
import com.example.retry.network.HttpManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity  {
    private Button btnGotoFrag;
    private Button btnParser;
    private Button btnAddCourse;
    private static final String URL = "https://api.npoint.io/0bd23b4265753b5c1db1";

    // metoda 2
    //astea 2 sunt in loc de thread-uri - varianta mai ok de facut
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Handler handler;

    //metoda 3 pt thread-uri : cea mai buna
    private final AsyncTaskRun asyncTaskRun = new AsyncTaskRun();

    //pt transfer intre activitati
    private ActivityResultLauncher<Intent> launcher;
    private Intent intent;
    private ListView listView;
    private List<Course> list = new ArrayList<>();

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

        handler = new Handler(Looper.getMainLooper());

        initComp();

    }

    private void initComp() {
        btnAddCourse = findViewById(R.id.btn_add_course);
        btnGotoFrag = findViewById(R.id.btn_goto_frag);
        btnParser =  findViewById(R.id.btn_parser);
        listView = findViewById(R.id.lv_course);

        //pas 1 init lansator + deschidere la apasare
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), getCallback());
        btnAddCourse.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), AddActivity.class);
            launcher.launch(intent);
        });

        CourseAdapter adapter = new CourseAdapter(getApplicationContext(), R.layout.lv_style,list,getLayoutInflater());
        listView.setAdapter(adapter);


        btnGotoFrag.setOnClickListener(v -> {

            //creez fragmentul pt a-l atasa de activitatea asta
            HomeFragment fragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();

        });

        btnParser.setOnClickListener(v -> {

//            varianta 1 cu thread
//            Thread thread = new Thread(() -> {
//                HttpManager httpManager = new HttpManager(URL);
//                String result = httpManager.call();
//                runOnUiThread(getAction(result));
//            });
//            thread.start();

////          varianta 2 cu executors si handle
//            executor.execute(() -> {
//                HttpManager httpManager = new HttpManager(URL);
//                String result = httpManager.call();
//                handler.post(getAction(result));
//            });

//          varianta 3 cu AsyncTaskRunner
            HttpManager httpManager = new HttpManager(URL);
            asyncTaskRun.executeAsync(httpManager, getActionVersiuneOK());

        });
    }

    private ActivityResultCallback<ActivityResult> getCallback() {
        return result -> {
            //pas 4
            if(result.getResultCode() == RESULT_OK && result.getData() != null){
                //citesc cursul
                Course course = (Course) result.getData().getSerializableExtra("list_course");
                list.add(course);
                //notificare adaptor pt modificare
                ArrayAdapter<Course> adapter = (ArrayAdapter<Course>) listView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        };
    }

    private Callback<String> getActionVersiuneOK(){
        return result -> {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        };
    }
    @NonNull
    private Runnable getAction(String result) {
        return () -> {
            Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        };
    }


}