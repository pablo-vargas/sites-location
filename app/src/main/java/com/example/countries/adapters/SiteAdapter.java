package com.example.countries.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {
    List<Country> countries;
    Listener listener;


    public SiteAdapter(List<Country> countries) {
        this.countries = countries;
    }

    public SiteAdapter(List<Country> countries, Listener listener) {
        this.countries = countries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_site, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Country country = countries.get(position);
        holder.textCountry.setText("Pais: "+country.getCountry());
        holder.textCity.setText("Ciudad: "+country.getCity());
        holder.textAddress.setText("Direccion: "+country.getAddress());
        Picasso.get().load(country.getImage()).into(holder.imageView);
        holder.textLocation.setOnClickListener(v -> listener.showLocation(country));
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textCountry;
        private TextView textCity;
        private TextView textAddress,textLocation;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textCountry = itemView.findViewById(R.id.textCountry);
            textCity = itemView.findViewById(R.id.textCity);
            textAddress = itemView.findViewById(R.id.textAddress);
            textLocation = itemView.findViewById(R.id.textLocation);
            imageView = itemView.findViewById(R.id.imageSite);

        }
    }
    public interface Listener{
        void showLocation(Country country);
    }
}
