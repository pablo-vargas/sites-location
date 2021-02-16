package com.example.countries.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.countries.R;
import com.example.countries.contract.SaveSiteContract;
import com.example.countries.model.AddCountryViewModel;
import com.example.countries.model.Country;
import com.example.countries.presenter.SaveSitePresenter;
import com.example.countries.shared.CustomToast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import static android.app.Activity.RESULT_OK;


public class SaveCountryFragment extends Fragment implements SaveSiteContract.View {


    private TextInputEditText textCountry;
    private TextInputEditText textCity;
    private TextInputEditText textAddress;
    private MaterialButton btnAddImage,btnSave;
    private ImageView imageSite;
    private MaterialButton materialButton;
    private final static int IMAGE = 10;
    private static final int RP_STORAGE = 121;
    Uri pathImage;
    private LinearLayout layoutLoader;
    private TextView textLoader;

    private SaveSitePresenter presenter;
    AddCountryViewModel addCountryViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_country, container, false);
        addCountryViewModel = new ViewModelProvider(getActivity()).get(AddCountryViewModel.class);

        initView(view);

        presenter = new SaveSitePresenter(this);
        return view;
    }

    private void initView(View view) {
        textCountry = view.findViewById(R.id.textCountry);
        textCity = view.findViewById(R.id.textCity);
        textAddress = view.findViewById(R.id.textAddress);
        btnAddImage = view.findViewById(R.id.btnAddImage);
        imageSite = view.findViewById(R.id.imageSite);
        btnSave = view.findViewById(R.id.btnSave);
        layoutLoader = view.findViewById(R.id.layoutLoader);
        textLoader = view.findViewById(R.id.textLoader);


        Country country = addCountryViewModel.getCountry().getValue();
        textAddress.setText(country.getAddress());
        textCountry.setText(country.getCountry());
        textCity.setText(country.getCity());
        btnAddImage.setOnClickListener(v -> checkPermissionStorage());
        btnSave.setOnClickListener(view1 -> {
            try {
                if (pathImage != null) {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), pathImage);
                    presenter.save(
                            textCountry.getText().toString(),
                            textCity.getText().toString(),
                            textAddress.getText().toString(),
                            bitmap,country);
                }else{
                    hasError("Debe seleccionar una imagen");
                }
            }catch (Exception e){
                hasError(e.getMessage());
            }
        });
    }

    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(i, "Seleccione un archivo de texto"), IMAGE);
    }
    private void checkPermissionStorage() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        RP_STORAGE);
                return;
            }
        }
        openGallery();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE && resultCode ==RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            pathImage = filePath;
            Picasso.get().load(pathImage).into(imageSite);
        }
        if (requestCode == RP_STORAGE && resultCode ==RESULT_OK ) {
            openGallery();
        }
    }


    @Override
    public void showLoader(String textLoader) {
        this.textLoader.setText(textLoader);
        layoutLoader.setVisibility(View.VISIBLE);
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
    public void successFul(String message) {
        new CustomToast().custom(getContext(),"success",message).show();
        getActivity().onBackPressed();
        addCountryViewModel.setNewCountry(null);
    }

    @Override
    public void errorCountry(String error) {
        textCountry.setError(error);
    }

    @Override
    public void errorCity(String error) {
        textCity.setError(error);
    }

    @Override
    public void errorAddress(String error) {
        textAddress.setError(error);
    }
}