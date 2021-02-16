package com.example.countries;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.countries.activities.AddActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton btnAdd;
    private MaterialButton btnList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btnAdd = findViewById(R.id.btnAdd);
        btnList = findViewById(R.id.btnList);
        btnAdd.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AddActivity.class));
        });
        btnList.setOnClickListener(v -> {

        });
    }
}