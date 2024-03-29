package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class HotelsResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_results);

        Intent intent = getIntent();
        DbHelper dbHelper = new DbHelper(HotelsResults.this);

        ArrayList<HotelRoom> rooms = getRooms(intent, dbHelper);

        RecyclerView recyclerView = findViewById(R.id.hotelCards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter = new MyAdapter(this, rooms);
        recyclerView.setAdapter(adapter);
    }

    public ArrayList<HotelRoom> getRooms(Intent intent, DbHelper dbHelper) {
        // Initing db connection and cursor
        ArrayList<HotelRoom> result = new ArrayList<HotelRoom>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        // Parcing BookingRequest
        BookingRequest bookingRequest = intent.getSerializableExtra("BR", BookingRequest.class);
        Log.v("HotelsResults.getRooms", bookingRequest.getCity() + "|" + bookingRequest.getInDay() + "|" + bookingRequest.getOutDay() + "|" + bookingRequest.getGuests());
        String city = bookingRequest.getCity();
        String strArrival = bookingRequest.getInDay() + "";
        String strDeparture = bookingRequest.getOutDay() + "";
        String strGuests = bookingRequest.getGuests() + "";
        Log.v("String Arr and Dep", strArrival + "|" + strDeparture);
        // Getting table by BookingResults restrictions
        if (city.isEmpty()) {
            String sql = "SELECT room.id AS id, city.city AS city, room.places AS places, room.imgId AS imgId " +
                    "FROM room " +
                    "JOIN city ON room.cityId = city.id " +
                    "LEFT JOIN reservations ON room.id = reservations.roomId " +
                    "WHERE room.places >= ?" +
                    "AND ((? < COALESCE(reservations.inDay, 0) AND ? < COALESCE(reservations.outDay, 0)) " +
                    "OR (? > COALESCE(reservations.inDay, 0) AND ? > COALESCE(reservations.outDay, 0))) ";
            cursor = db.rawQuery(sql, new String[] {strGuests, strArrival, strDeparture, strArrival, strDeparture});
        } else {
            String sql = "SELECT room.id AS id, city.city AS city, room.places AS places, room.imgId AS imgId " +
                    "FROM room " +
                    "JOIN city ON room.cityId = city.id " +
                    "LEFT JOIN reservations ON room.id = reservations.roomId " +
                    "WHERE room.places >= ? " +
                    "AND city.city = ?" +
                    "AND ((? < COALESCE(reservations.inDay, 0) AND ? < COALESCE(reservations.outDay, 0)) " +
                    "OR (? > COALESCE(reservations.inDay, 0) AND ? > COALESCE(reservations.outDay, 0))) ";
            cursor = db.rawQuery(sql, new String[] {strGuests, city, strArrival, strDeparture, strArrival, strDeparture});
        }

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int numberIndex = cursor.getColumnIndex("id");
                int cityIdIndex = cursor.getColumnIndex("city");
                int placesIndex = cursor.getColumnIndex("places");
                int imgIdIndex = cursor.getColumnIndex("imgId");

                if (numberIndex != -1 && cityIdIndex != -1 && placesIndex != -1 && imgIdIndex != -1) {
                    result.add(new HotelRoom(cursor.getInt(numberIndex),
                            cursor.getString(cityIdIndex),
                            cursor.getInt(placesIndex),
                            cursor.getInt(imgIdIndex)));
                }
            } while (cursor.moveToNext());
            cursor.close();
            return result;
        }
        return null;
    }

    public void toProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void backOnMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}