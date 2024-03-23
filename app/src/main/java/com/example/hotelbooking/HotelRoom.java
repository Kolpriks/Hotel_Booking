package com.example.hotelbooking;

public class HotelRoom {
    public Integer id;
    public Integer cityId;
    public Integer places;

    public HotelRoom(Integer id, Integer cityId, Integer places) {
        this.id = id;
        this.cityId = cityId;
        this.places = places;
    }

    public int getId() {
        return this.id;
    }

    public int getCityId() {
        return this.cityId;
    }

    public int getPlaces() {
        return this.places;
    }
}
