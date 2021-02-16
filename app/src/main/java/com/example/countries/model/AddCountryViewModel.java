package com.example.countries.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCountryViewModel extends ViewModel  {
    private MutableLiveData<Country> newCountry;

    public AddCountryViewModel() {
        newCountry = new MutableLiveData<>();
    }

    public void setNewCountry(Country country){
        this.newCountry.postValue(country);
    }

    public LiveData<Country> getCountry(){return this.newCountry;}
}
