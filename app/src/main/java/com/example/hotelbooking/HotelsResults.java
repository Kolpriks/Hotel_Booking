package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class HotelsResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_results);

        Intent intent = getIntent();
        DbHelper dbHelper = new DbHelper(HotelsResults.this);

        ArrayList<HotelRoom> rooms = getRooms(intent, dbHelper);

        // Temporary code inside: if { ... }
        if (rooms != null) {
            TextView textView = findViewById(R.id.textViewInfo);
            String rresult =  rooms.get(0).city + " \n" + rooms.get(0).places;
            textView.setText(rresult);
            ImageView imageView = findViewById(R.id.imageView);

            String path = Environment.getExternalStorageDirectory()+ "app/src/main/res/drawable/image" + rooms.get(0).imgId + ".jpg";
            File imgFile = new File(path);
            if(imgFile.exists())
            {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView.setImageBitmap(myBitmap);
            }
        }
    }

    public ArrayList<HotelRoom> getRooms(Intent intent, DbHelper dbHelper) {

        ArrayList<HotelRoom> result = new ArrayList<HotelRoom>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // TODO: Maybe compose into model object
        String city = intent.getStringExtra("city");
        String arrive = intent.getStringExtra("arrive");
        String departure = intent.getStringExtra("departure");
        String guests = intent.getStringExtra("guests");

        if (city.isEmpty() && arrive.isEmpty() && departure.isEmpty() && guests.isEmpty()) {

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