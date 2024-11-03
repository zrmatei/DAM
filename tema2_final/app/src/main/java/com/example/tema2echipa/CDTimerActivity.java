package com.example.tema2echipa;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tema2echipa.classes.ListaAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CDTimerActivity extends AppCompatActivity {
    private boolean timerRunning = false;
    private boolean timerPaused = false;
    private long timeLeftInMillis;
    private CountDownTimer countDownTimer;

    private FloatingActionButton fabPrev;
    private ToggleButton togglebtn;
    private ToggleButton togglePausebtn;
    private EditText etHour, etMinute, etSecond;
    private ListaAdapter adapter;
    private List<String> taskData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cdtimer);

        togglebtn = findViewById(R.id.chingaru_george_toggle);
        togglePausebtn = findViewById(R.id.chingaru_george_toggle_pause);
        etHour = findViewById(R.id.chingaru_george_hour);
        etMinute = findViewById(R.id.chingaru_george_minute);
        etSecond = findViewById(R.id.chingaru_george_second);
        togglebtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!timerRunning) {
                    setTimerFromInput();
                    startTimer();
                } else {
                    Toast.makeText(CDTimerActivity.this, R.string.timer_is_already_running, Toast.LENGTH_SHORT).show();
                }
            } else {
                resetTimer();
            }
        });

        togglePausebtn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                pauseTimer();
            } else {

                resumeTimer();
            }
        });

        fabPrev = findViewById(R.id.chingaru_george_btn_prev);
        fabPrev.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    private void setTimerFromInput() {
        int hours = parseInput(etHour);
        int minutes = parseInput(etMinute);
        int seconds = parseInput(etSecond);
        if (hours < 0 || hours > 24) {
            Toast.makeText(getApplicationContext(), R.string.msg_ora, Toast.LENGTH_SHORT).show();
            return;
        }
        if (minutes < 0 || minutes > 59) {
            Toast.makeText(getApplicationContext(), R.string.msg_min, Toast.LENGTH_SHORT).show();
            return;
        }
        if (seconds < 0 || seconds > 59) {
            Toast.makeText(getApplicationContext(), R.string.msg_sec, Toast.LENGTH_SHORT).show();
            return;
        }
        timeLeftInMillis = (hours * 3600 + minutes * 60 + seconds) * 1000;
        etHour.setFocusable(false);
        etMinute.setFocusable(false);
        etSecond.setFocusable(false);
    }

    private int parseInput(EditText editText) {

        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            return 0;
        }
        else
        {
            return Integer.parseInt(input);
        }


    }
    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                togglebtn.setChecked(false);
                togglePausebtn.setChecked(false);
                etHour.setFocusableInTouchMode(true);
                etMinute.setFocusableInTouchMode(true);
                etSecond.setFocusableInTouchMode(true);

                MediaPlayer alarm = MediaPlayer.create(CDTimerActivity.this, R.raw.alarm);
                alarm.start();

                //razvan - adaugat pt stergere prim element lista
                Intent intent = new Intent(CDTimerActivity.this, ListaActivity.class);
                intent.putExtra("remove_item", true);
                startActivity(intent);
            }
        }.start();
        timerRunning = true;
        timerPaused = false;
    }


    private void pauseTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timerPaused = true;
        timerRunning = false;
    }

    private void resumeTimer() {
        if (timerPaused) {
            startTimer();
            timerPaused = false;
        }
    }

    private void resetTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        timeLeftInMillis = 0;
        timerRunning = false;
        timerPaused = false;
        etHour.setFocusableInTouchMode(true);
        etMinute.setFocusableInTouchMode(true);
        etSecond.setFocusableInTouchMode(true);

        updateTimerText();
    }

    private void updateTimerText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
        etHour.setText(String.format(Locale.getDefault(),"%02d",hours));
        etMinute.setText(String.format(Locale.getDefault(),"%02d",minutes));
        etSecond.setText(String.format(Locale.getDefault(),"%02d",seconds));
    }
}