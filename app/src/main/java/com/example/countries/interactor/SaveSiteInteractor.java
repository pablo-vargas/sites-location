package com.example.countries.interactor;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.countries.contract.SaveSiteContract;
import com.example.countries.model.Country;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class SaveSiteInteractor {

    private SaveSiteContract.Listener listener;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    public SaveSiteInteractor(SaveSiteContract.Listener listener) {
        this.listener = listener;
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void save(String country, String city, String address, Bitmap image,double lat,double lng){
        boolean isValid = true;
        if(country.trim().isEmpty()){
            listener.errorCountry("Este campo no puede ir vacío");
            isValid = false;
        }
        if(city.trim().isEmpty()){
            listener.errorCountry("Este campo no puede ir vacío");
            isValid = false;
        }
        if(address.trim().isEmpty()){
            listener.errorCountry("Este campo no puede ir vacío");
            isValid = false;
        }
        if(isValid){
            Country new_country = new Country(country,city,address);
            new_country.setLatitude(lat);
            new_country.setLongitude(lng);
            UploadTask uploadTask = storageReference.child("sites").child(new Date().getTime()+".jpg").putBytes(getImageBytes(image));
            uploadTask.addOnSuccessListener(taskSnapshot -> taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uriUpload -> {
                if(uriUpload != null){
                    new_country.setImage(uriUpload.toString());
                    databaseReference.child("sites").push().setValue(new_country).addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            listener.successFul("Sitio creado correctamente");
                        }else{
                            listener.hasError("Error al guardar product");
                        }
                    });
                }else{
                    listener.hasError("Error al cargar imagen");
                }
            }));
        }
    }

    private byte[] getImageBytes(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        return imageBytes;
    }
}
