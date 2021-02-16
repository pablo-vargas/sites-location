package com.example.countries.presenter;

import android.graphics.Bitmap;

import com.example.countries.contract.SaveSiteContract;
import com.example.countries.interactor.SaveSiteInteractor;
import com.example.countries.model.Country;

public class SaveSitePresenter implements SaveSiteContract.Listener {

    private SaveSiteContract.View view;
    private SaveSiteInteractor interactor;

    public SaveSitePresenter(SaveSiteContract.View view) {
        this.view = view;
        interactor = new SaveSiteInteractor(this);
    }
    public void save(String country, String city, String address, Bitmap image, Country site){
        view.showLoader("Guardando nuevo sitio");
        interactor.save(country,city,address,image,site.getLatitude(),site.getLongitude());
    }
    @Override
    public void hasError(String error) {
        view.hideLoader();
        view.hasError(error);
    }

    @Override
    public void successFul(String message) {
        view.hideLoader();
        view.successFul(message);

    }

    @Override
    public void errorCountry(String error) {
        view.hideLoader();
        view.errorCountry(error);
    }

    @Override
    public void errorCity(String error) {
        view.hideLoader();
        view.errorCity(error);
    }

    @Override
    public void errorAddress(String error) {
        view.hideLoader();
        view.errorAddress(error);
    }
}
