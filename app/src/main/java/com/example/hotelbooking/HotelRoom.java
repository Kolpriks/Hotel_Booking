package com.example.hotelbooking;

import android.graphics.Bitmap;

public class HotelRoom {

    private int id;
    private String city;
    private int places;
    private int imgId;

    public HotelRoom(Integer id, String city, Integer places, Integer imgId) {
        this.id = id;
        this.city = city;
        this.places = places;
        this.imgId = imgId;
    }

    public int getId() {
        return this.id;
    }

    public String getCity() {
        return this.city;
    }

    public int getPlaces() {
        return this.places;
    }

    public int getImgId() {return this.imgId;}
}
