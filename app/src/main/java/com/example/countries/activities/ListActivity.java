package com.example.countries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countries.R;
import com.example.countries.adapters.SiteAdapter;
import com.example.countries.model.Country;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements SiteAdapter.Listener, OnMapReadyCallback {

    LinearLayout layoutLoader;
    TextView textEmpty,textLoader;
    RecyclerView recyclerSites;

    List<Country> countries;
    SiteAdapter adapter;

    private GoogleMap map;
    private SupportMapFragment sMapFragment;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        layoutLoader = findViewById(R.id.layoutLoader);
        textEmpty = findViewById(R.id.textEmpty);
        textLoader = findViewById(R.id.textLoader);
        recyclerSites = findViewById(R.id.recyclerSites);
        countries = new ArrayList<>();
        adapter = new SiteAdapter(countries,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerSites.setLayoutManager(layoutManager);
        recyclerSites.setAdapter(adapter);
        initMap();
        getSites();

    }
    private void initMap(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_map_location,null);
        int id = view.findViewById(R.id.map).getId();
        sMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(id);
        alertDialog = new AlertDialog.Builder(this).setView(view).create();
        ImageButton imageButton = view.findViewById(R.id.btnClose);
        imageButton.setOnClickListener(v -> alertDialog.cancel());
        sMapFragment.getMapAsync(this);
    }

    private void getSites(){
        showLoader();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                countries.clear();
                hideLoader();
                if(snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                        countries.add(new Country(dataSnapshot));
                    adapter.notifyDataSetChanged();
                }else{
                    listEmpty();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                hideLoader();
            }
        };
        FirebaseDatabase.getInstance().getReference("sites").addListenerForSingleValueEvent(valueEventListener);
    }
    private void showLoader(){
        layoutLoader.setVisibility(View.VISIBLE);
    }
    private void hideLoader(){
        layoutLoader.setVisibility(View.GONE);
    }
    private void listEmpty(){
        textEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLocation(Country country) {
        map.clear();
        map.addMarker(new MarkerOptions().title(country.getAddress()).position(new LatLng(country.getLatitude(),country.getLongitude())));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(country.getLatitude(),country.getLongitude()))
                        .zoom(13f)
                        .build()
        ));
        alertDialog.show();
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
    }
}