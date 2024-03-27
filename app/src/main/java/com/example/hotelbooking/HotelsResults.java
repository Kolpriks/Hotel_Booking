package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
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

        ArrayList<HotelRoom> result = new ArrayList<HotelRoom>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // TODO: Maybe compose into model object
        BookingRequest bookingRequest = intent.getSerializableExtra("BR", BookingRequest.class);

        Log.v("HotelsResults.getRooms", bookingRequest.getCity() + "|" + bookingRequest.getInDay() + "|" + bookingRequest.getOutDay() + "|" + bookingRequest.getGuests());
        String city = bookingRequest.getCity();
        long arrive = bookingRequest.getInDay();
        long departure = bookingRequest.getOutDay();
        int guests = bookingRequest.getGuests();

        if (city.isEmpty() && guests == 1) {

            String sql = "SELECT room.id AS id, city.city, room.places, room.imgId " +
                    "FROM room JOIN city " +
                    "ON room.cityId = city.id";
            Cursor cursor = db.rawQuery(sql, null);

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
            } else {
                // TODO: Sort parced results by in and out time
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return null;
    }

    public void toProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}