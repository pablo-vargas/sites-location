package com.example.countries.interactor;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.countries.R;
import com.example.countries.contract.MapContract;
import com.example.countries.model.Country;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapInteractor {
    private MapContract.Listener listener;
    RequestQueue queue;
    Context context;
    Geocoder geocoder;
    List<Address> addresses;
    public MapInteractor(MapContract.Listener listener,Context context) {
        this.listener = listener;
        this.context = context;
        queue = Volley.newRequestQueue(context);
        geocoder = new Geocoder(context);
    }

    public void getData(LatLng latLng){
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                addresses =geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                Log.d("ADDRESS",address+" - "+city+" - "+country+" - "+state);
                Country country_location = new Country(country,city,address);
                country_location.setLatitude(latLng.latitude);
                country_location.setLongitude(latLng.longitude);
                listener.dataLocation(country_location);
            } catch (IOException e) {
                e.printStackTrace();
                listener.hasError(e.getMessage());
            }

        },800);

    }

    public void getDataVolley(LatLng latLng){
        String API_KEY = context.getString(R.string.google_maps_key);
        String location =latLng.latitude+","+latLng.longitude;
        String URL = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+location+"&location_type=ROOFTOP&result_type=street_address&key="+API_KEY;
        StringRequest request = new StringRequest(Request.Method.GET, URL, response -> {
            try {
                JSONArray object = new JSONArray(response);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("ERROR_REQUEST",e.getMessage());
            }
        }, error -> {
            listener.hasError("Error: "+error.getMessage());
        });

        queue.add(request);
    }

}
