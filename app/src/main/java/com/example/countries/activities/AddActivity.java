package com.example.countries.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.countries.R;
import com.example.countries.fragments.MapFragment;
import com.example.countries.fragments.SaveCountryFragment;
import com.example.countries.model.AddCountryViewModel;

public class AddActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    AddCountryViewModel addCountryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.contentView,new MapFragment(),"map").commit();
        addCountryViewModel = new ViewModelProvider(this).get(AddCountryViewModel.class);

        addCountryViewModel.getCountry().observe(this,country -> {
            if(country != null){
                fragmentManager.beginTransaction().add(R.id.contentView,new SaveCountryFragment()).addToBackStack("map").commit();
            }else{
                onBackPressed();
            }
        });
    }
}