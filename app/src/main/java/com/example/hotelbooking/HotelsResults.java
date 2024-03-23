package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HotelsResults extends AppCompatActivity {

    ArrayList<HotelRoom> rooms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_results);
        Intent intent = getIntent();


        DbHelper dbHelper = new DbHelper(HotelsResults.this);
        Log.v("HotelsResults.onCreate", "DbHelper Created");
        rooms = getRooms(intent, dbHelper);

        if (rooms != null) {
            // TODO: Empty rooms realisation
            for (HotelRoom room : rooms ){
                Log.v("HotelsResults", room.getId() + "|" + room.getCityId() + "|" + room.getPlaces());
            }
            TextView textView = findViewById(R.id.textViewInfo);
            String rresult = rooms.get(0).id + " \n" +
                    rooms.get(0).cityId + " \n" +
                    rooms.get(0).places;
            textView.setText(rresult);
        }

    }

    public ArrayList<HotelRoom> getRooms(Intent intent, DbHelper dbHelper) {
        ArrayList<HotelRoom> result = new ArrayList<HotelRoom>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String city = intent.getStringExtra("city");
        String arrive = intent.getStringExtra("arrive");
        String departure = intent.getStringExtra("departure");
        String guests = intent.getStringExtra("guests");


        // String logOut = city + "|" + arrive + "|" + departure + "|" + guests + "|";
        // Log.v("HotelResults", logOut);

        if (city.isEmpty() && arrive.isEmpty() && departure.isEmpty() && guests.isEmpty()) {
            // Log.v("HotelsResults", "Inputed fields is empty");
            Cursor cursor = db.query("room", new String[] {"id", "cityId", "places"},
                    null, null,
                    null, null, null);

            // String cursorFromQuerry = cursor.toString();
            // Log.v("HotelResults.AfterQuerry", cursorFromQuerry);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Log.v("Cursor", "Cursor read cycle");
                    int numberIndex = cursor.getColumnIndex("id");
                    int cityIdIndex = cursor.getColumnIndex("cityId");
                    int placesIndex = cursor.getColumnIndex("places");

                    // String cursorLogs = numberIndex + "|" + cityIdIndex + "|" + placesIndex;
                    // Log.v("HotelResults", cursorLogs);

                    if (numberIndex != -1 && cityIdIndex != -1 && placesIndex != -1) {
                        result.add(new HotelRoom(cursor.getInt(numberIndex), cursor.getInt(cityIdIndex), cursor.getInt(placesIndex)));
                    }
                } while (cursor.moveToNext());
                cursor.close();
                return result;
            } else {
                if (cursor != null) {
                    cursor.close();
                }
                 // Номер отеля не найден
            }
        }
        return null;
    }

    public void toProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
}