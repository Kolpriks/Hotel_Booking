package com.example.hotelbooking;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.util.Calendar;
import android.util.Log;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Init {
    private DbHelper dbHelper;

    public Init(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                dbHelper.onUpgrade(db, 1, 1);

                String [] cities = {"Москва", "Санкт-Питербург", "Казань", "Тверь", "Калуга", "Волгоград"};
                int [] users = {1, 2, 3};
                Calendar calendar = Calendar.getInstance();
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

                    values.put("email", "admin@mail.ru");
                    values.put("name", "Andmin");
                    values.put("password", "adminpwd");
                    values.put("admin", 1);
                    db.insert("users", null, values);
                    values.remove("email");
                    values.remove("name");
                    values.remove("password");
                    values.remove("admin");

                    Log.v("After users in", "Users incerted");

                    long inDay = LocalDateTime.of(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            12,
                            0
                    ).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
                    values.put("inDay", inDay);

                    calendar.add(Calendar.DAY_OF_MONTH, 25);

                    long outDay = LocalDateTime.of(
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH),
                            12,
                            0
                    ).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
                    values.put("outDay", outDay);

                    for (int user : users) {
                        values.put("userId", user);
                        values.put("roomId", 1 + user);
                        db.insert("reservations", null, values);
                        values.remove("userId");
                        values.remove("roomId");
                        Log.v("Reservations input", "Reservations for user" + user);
                    }

                    values.remove("inDay");
                    values.remove("outDay");

                    Log.v("After reservations in", "reservations incerted");

                } catch(SQLiteException e) {
                    Log.e("Init.Init", "Error with DB initialisation");
                } finally {
                    db.close();
                }
            }
        }).start();
    }
}
