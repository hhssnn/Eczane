package com.hsncmz.eczane;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsncmz.eczane.model.Eczane;

import java.util.ArrayList;
import java.util.List;

public class SamsunMapFragment extends SupportMapFragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap googleMap;
    private List<Eczane> eczaneList = new ArrayList<>();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .build();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap map) {
                googleMap = map;

                updateLocation();
            }
        });
    }

    private void updateLocation() {
        LatLng bafraLocation = new LatLng(41.561840, 35.905521);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bafraLocation, 14));
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_red_48dp))
                .position(bafraLocation));

        fetchFirebaseDb();
    }


    public void setStationMarker() {
        googleMap.clear();

        for (Eczane item : eczaneList) {
            LatLng position = new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
            googleMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_green_48dp))
                    .title(item.getName())
                    //.snippet("Adres: " + item.getAddress() + "\nTelefon: 03625438289")
                    .position(position));
        }
    }

    private void fetchFirebaseDb() {
        DatabaseReference reference = mDatabase.getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.e("Firebase", "onDataChange: " + data.getValue());
                    //eczaneList.add(data.getValue(Eczane.class));
                }
                //setStationMarker();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("FFF", "onCancelled: " + databaseError.toString());
            }
        });
    }



    @Override
    public boolean onMarkerClick(Marker marker) {
        Log.d("Eczane", "onMarkerClick: " + marker.getTitle());
        for (Eczane item : eczaneList) {
            if (item.getName().equals(marker.getTitle())) {
                new AlertDialog.Builder(getContext())
                        .setCancelable(true)
                        .setTitle(marker.getTitle())
                        .setMessage("<b>Adres: </b>" + item.getAddress() + "\nTelefon: 012323131112")
                        .setPositiveButton(android.R.string.yes, null)
                        //.setNeutralButton("Mesaj Bırak", null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        }
        return false;
    }
}