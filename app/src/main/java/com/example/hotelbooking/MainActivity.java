package com.example.hotelbooking;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DbHelper dbHelper = new DbHelper(this);
            dbInit(dbHelper);

        } catch (SQLiteException e) {
            Log.e("MainActivity.onCreate", "Error whith connecting to database");
        }


        Button buttonInDate = findViewById(R.id.buttonInDay);
        Button buttonOutDate = findViewById(R.id.buttnOutDay);

        buttonInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputInDateDialog();
            }
        });

        buttonOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputOutDateDialog();
            }
        });
    }

    // Maybe put in some "presets" class
    public void dbInit(DbHelper dbHelper) {
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                        if (cityId != -1) {
                            values.put("cityId", cityId);

                            values.put("places", 2);
                            values.put("imgId", 1);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.remove("imgId");

                            values.put("places", 3);
                            values.put("imgId", 2);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.remove("imgId");

                            values.put("places", 4);
                            values.put("imgId", 3);
                            db.insert("room", null, values);
                            values.remove("places");
                            values.remove("imgId");

                            values.remove("cityId");

                            Log.v("After rooms in", "Rooms incerted");
                        }
                    }

                    values.put("email", "user1@mail.ru");
                    values.put("name", "User1");
                    values.put("password", "user1pwd");
                    values.put("admin", 0);
                    db.insert("users", null, values);
                    values.remove("email");
                    values.remove("name");
                    values.remove("password");
                    values.remove("admin");

                    values.put("email", "user2@mail.ru");
                    values.put("name", "User2");
                    values.put("password", "user2pwd");
                    values.put("admin", 0);
                    db.insert("users", null, values);
                    values.remove("email");
                    values.remove("name");
                    values.remove("password");
                    values.remove("admin");

                    values.put("email", "admin1@mail.ru");
                    values.put("name", "Andmin1");
                    values.put("password", "admin1pwd");
                    values.put("admin", 1);
                    db.insert("users", null, values);
                    values.remove("email");
                    values.remove("name");
                    values.remove("password");
                    values.remove("admin");

                } catch(SQLiteException e) {
                    Toast.makeText(MainActivity.this, "Init internal error", Toast.LENGTH_SHORT).show();
                } finally {
                    db.close();
                }
            }
        }).start();
    }

    public void toProfile(View view){
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    public void getInfo(View view){
        int places = 1;
        String city = ((EditText) findViewById(R.id.editTextCity)).getText().toString();
        String guests = ((EditText) findViewById(R.id.editTextGuests)).getText().toString();

        Log.v("MainActivity.getInfo", "|" + guests + "|");

        if (guests.isEmpty()) {
            Log.v("MainActivity.getInfo.guests", "IsEmpty");
        } else {
            places = Integer.parseInt(guests);
        }

        SharedPreferences prefsIn = getSharedPreferences("inDay", MODE_PRIVATE);
        long inDateInSec = dateToSec(prefsIn);
        SharedPreferences prefsOut = getSharedPreferences("outDay", MODE_PRIVATE);
        long outDateInSec = dateToSec(prefsIn);

        BookingRequest bookingRequest = new BookingRequest(city, inDateInSec, outDateInSec, places);

//        Log.v("MainActivity.getInfo", bookingRequest.getCity() + "|" + bookingRequest.getInDay() + "|" + bookingRequest.getOutDay() + "|" + bookingRequest.getGuests());

        // TODO: Maybe compose to model object
        Intent intent = new Intent(this, HotelsResults.class);
        intent.putExtra("BR", bookingRequest);
        startActivity(intent);
    }

    private long dateToSec(SharedPreferences prefs) {
        int day = prefs.getInt("Day", -1);
        int month = prefs.getInt("Month", -1);
        int year = prefs.getInt("Year", -1);

        if (day == -1 || month == -1 || year == -1) {
            return 0;
        }

        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 12, 0);
        Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
        return instant.getEpochSecond();
    }

    private void inputInDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView editText = findViewById(R.id.TextArrive);
                editText.setText(year + "." + month + "." + dayOfMonth);
                SharedPreferences prefs = getSharedPreferences("inDay", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Day", dayOfMonth);
                editor.putInt("Month", month + 1);
                editor.putInt("Year", year);
                editor.apply();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    private void inputOutDateDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView editText = findViewById(R.id.TextDeparture);
                editText.setText(year + "." + month + "." + dayOfMonth);
                SharedPreferences prefs = getSharedPreferences("outDay", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Day", dayOfMonth);
                editor.putInt("Month", month + 1);
                editor.putInt("Year", year);
                editor.apply();
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}