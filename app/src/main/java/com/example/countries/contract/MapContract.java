package com.example.countries.contract;

import com.example.countries.model.Country;

public interface MapContract {
    interface View{
        void showLoader(String textLoader);
        void hideLoader();
        void hasError(String error);
        void dataLocation(Country country);
    }
    interface Listener{
        void hasError(String error);
        void dataLocation(Country country);
    }
}
