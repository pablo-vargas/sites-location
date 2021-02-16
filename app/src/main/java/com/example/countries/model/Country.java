package com.example.countries.model;

import com.google.firebase.database.DataSnapshot;

public class Country {
    private String id;
    private String country;
    private String city;
    private String address;
    private String image;
    private Double latitude;
    private Double longitude;


    public Country(String country, String city, String address) {
        this.country = country;
        this.city = city;
        this.address = address;
    }
    public Country(DataSnapshot snapshot){
        this.id = snapshot.getKey();
        this.city = snapshot.child("city").getValue().toString();
        this.address = snapshot.child("address").getValue().toString();
        this.country = snapshot.child("country").getValue().toString();
        this.image = snapshot.child("image").getValue().toString();
        this.latitude = Double.valueOf(snapshot.child("latitude").getValue().toString());
        this.longitude = Double.valueOf(snapshot.child("longitude").getValue().toString());

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
