package com.example.countries.contract;

public interface SaveSiteContract {
    interface View{
        void showLoader(String textLoader);
        void hideLoader();
        void hasError(String error);
        void successFul(String message);

        void errorCountry(String error);
        void errorCity(String error);
        void errorAddress(String error);


    }
    interface Listener{
        void hasError(String error);
        void successFul(String message);

        void errorCountry(String error);
        void errorCity(String error);
        void errorAddress(String error);
    }
}
