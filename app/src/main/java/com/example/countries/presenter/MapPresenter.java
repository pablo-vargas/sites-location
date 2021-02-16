package com.example.countries.presenter;

import android.content.Context;

import com.example.countries.contract.MapContract;
import com.example.countries.interactor.MapInteractor;
import com.example.countries.model.Country;
import com.google.android.gms.maps.model.LatLng;

public class MapPresenter implements MapContract.Listener {

    private MapContract.View view;
    private final MapInteractor interactor;

    public MapPresenter(MapContract.View view, Context context) {
        this.view = view;
        interactor = new MapInteractor(this,context);
    }

    public void getData(LatLng latLng){
        view.showLoader("Buscando información");
        interactor.getData(latLng);
    }

    @Override
    public void hasError(String error) {
        view.hideLoader();
        view.hasError(error);
    }

    @Override
    public void dataLocation(Country country) {
        view.hideLoader();
        view.dataLocation(country);
    }
}
