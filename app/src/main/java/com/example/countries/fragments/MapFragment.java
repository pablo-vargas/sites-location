package com.example.countries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.countries.R;
import com.example.countries.contract.MapContract;
import com.example.countries.model.AddCountryViewModel;
import com.example.countries.model.Country;
import com.example.countries.presenter.MapPresenter;
import com.example.countries.shared.CustomToast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapFragment extends Fragment implements OnMapReadyCallback, MapContract.View {
    private GoogleMap map;
    private SupportMapFragment sMapFragment;

    AddCountryViewModel addCountryViewModel;
    private LinearLayout layoutLoader;
    private TextView textLoader;

    MapPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initView(view);
        presenter = new MapPresenter(this,getContext());
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(-19.0429, -65.2554))
                        .zoom(11f)
                        .build()
        ));
        map.setOnMapClickListener(latLng -> {
            map.clear();
            map.addMarker(new MarkerOptions().position(latLng).title(""));
            presenter.getData(latLng);
        });
    }

    @Override
    public void showLoader(String textLoader) {
        layoutLoader.setVisibility(View.VISIBLE);
        this.textLoader.setText(textLoader);
    }

    @Override
    public void hideLoader() {
        layoutLoader.setVisibility(View.GONE);
    }

    @Override
    public void hasError(String error) {
        new CustomToast().custom(getContext(),"error",error).show();
    }

    @Override
    public void dataLocation(Country country) {
        addCountryViewModel.setNewCountry(country);
    }

    private void initView(View view) {
        layoutLoader = view.findViewById(R.id.layoutLoader);
        textLoader = view.findViewById(R.id.textLoader);
        int id = view.findViewById(R.id.map).getId();
        sMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        sMapFragment.getMapAsync(this);

        addCountryViewModel = new ViewModelProvider(getActivity()).get(AddCountryViewModel.class);
    }
}