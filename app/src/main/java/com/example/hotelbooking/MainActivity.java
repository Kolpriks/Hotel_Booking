package com.example.hotelbooking;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            DbHelper dbHelper = new DbHelper(this);
            dbInit(dbHelper);
        } catch (SQLiteException e) {
            Log.e("MainActivity.onCreate", "Error whith opening db");
        }
    }

    public void dbInit(DbHelper dbHelper) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Получаем объект SQLiteDatabase для записи в базу данных
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                dbHelper.onUpgrade(db, 1, 1);

                String [] cities = {"Москва", "Санкт-Питербург", "Казань", "Тверь", "Калуга", "Волгоград"};
                long cityId;
                try {
                    ContentValues values = new ContentValues();
                    for (String city : cities) {
                        values.put("city", city);
                        cityId = db.insert("city", null, values);
                        values.remove("city");
                        Log.v("After City in", "City incerted");
                        if (cityId != -1) {
                            values.put("cityId", cityId);
                            values.put("places", 2);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.put("places", 3);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.put("places", 4);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.remove("cityId");
                            Log.v("After rooms in", "Rooms incerted");
                        }
                    }
                } catch(SQLiteException e) {
                    Toast.makeText(MainActivity.this, "Init internal error", Toast.LENGTH_SHORT).show();
                } finally {
                    // Закрываем БД
                    // db.close();
                }
            }
        }).start();
    }

    public void toProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    public void getInfo(View view){
        String city = ((EditText) findViewById(R.id.editTextCity)).getText().toString();
        String arrive = ((EditText) findViewById(R.id.editTextArrive)).getText().toString();
        String departure = ((EditText) findViewById(R.id.editTextDeparture)).getText().toString();
        String guests = ((EditText) findViewById(R.id.editTextGuests)).getText().toString();

        String message = " " + city + "\n" +
                " " + arrive +
                " " + departure +
                " " + guests;

        Intent intent = new Intent(this, HotelsResults.class);
        intent.putExtra("info", message);
        startActivity(intent);
    }
}