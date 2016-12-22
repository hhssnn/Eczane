package com.hsncmz.eczane.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Created by Hasan Çömez on 16/12/16.
 */

public class Eczane implements Serializable {

    private String name;
    private String address;
    private String latitude;
    private String longitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LatLng getLocation() {
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}
