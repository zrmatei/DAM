package com.example.finala.frags;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.finala.R;
import com.google.android.material.textfield.TextInputEditText;


public class LabFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    private TextInputEditText tietId, tietName, tietNumber;
    private RatingBar ratingBar;
    private Button btnSave;

    public LabFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("my_shared", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab, container, false);

        initComp(view);
        return view;
    }

    private void initComp(View view) {
        tietId = view.findViewById(R.id.tiet_id);
        tietName = view.findViewById(R.id.tiet_name);
        tietNumber = view.findViewById(R.id.tiet_number);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnSave = view.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> {
            int id = tietId.getText().toString() != null ? Integer.parseInt(tietId.getText().toString()) : 0;
            String name = tietName.getText().toString()  != null ? tietName.getText().toString() : "";
            int number = tietNumber.getText().toString() != null ? Integer.parseInt(tietNumber.getText().toString()) : 0;
            float rating = ratingBar.getRating();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", id);
            editor.putString("labName", name);
            editor.putInt("classNumber", number);
            editor.putFloat("rating", rating);

            editor.apply();
        });
    }
}