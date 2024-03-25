package com.example.hotelbooking;

import android.graphics.Bitmap;

public class HotelRoom {

    public int id;
    public String city;
    public int places;
    int imgId;

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
