package com.example.retry.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import com.example.retry.R;
import com.google.android.material.textfield.TextInputEditText;


public class HomeFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private TextInputEditText tietID, tietLabName, tietClassNo;
    private Button btnSaveFrag;
    private RatingBar ratingBar;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getContext().getSharedPreferences("home_fragment", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initComp(view);

        return view;
    }

    private void initComp(View view) {
        tietID = view.findViewById(R.id.tiet_id);
        tietLabName = view.findViewById(R.id.tiet_labname);
        tietClassNo = view.findViewById(R.id.tiet_classno);
        btnSaveFrag = view.findViewById(R.id.btn_save);
        ratingBar = view.findViewById(R.id.ratingBar);

        btnSaveFrag.setOnClickListener(v -> {
            //pregatirea variab pt sharedPref
            int id = tietID.getText().toString() != null ? Integer.parseInt(tietID.getText().toString()) : 0;
            String labName = tietLabName.getText().toString() != null ? tietLabName.getText().toString() : "";
            int number = tietClassNo.getText().toString() != null ? Integer.parseInt(tietClassNo.getText().toString()) : 0;
            float rating = ratingBar.getRating();

            //salvarea in sharedPref
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("id", id);
            editor.putString("labName", labName);
            editor.putInt("classNo", number);
            editor.putFloat("rating", rating);

            editor.apply();
        });

    }
}