package com.example.hotelbooking;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.util.Calendar;
import android.util.Log;
import com.example.hotelbooking.AESCrypt;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Init {
    private DbHelper dbHelper;

    public Init(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor сursor = db.rawQuery("SELECT * FROM city", null);
        Log.v("Init", сursor + "");
        if (сursor != null && сursor.moveToFirst()) {
            Log.v("Init", "Init unnesesery");
            return;
        }
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


            try {
                String encryptedPassword = AESCrypt.encrypt("user1pwd");
                values.put("password", encryptedPassword);
                values.put("email", "user1@mail.ru");
                values.put("name", "User1");
                values.put("admin", 0);
                db.insert("users", null, values);
                values.remove("email");
                values.remove("name");
                values.remove("password");
                values.remove("admin");
            } catch (Exception e) {
                Log.e("Init", "ununable to init user 1 password");
            }

            try {
                String encryptedPassword = AESCrypt.encrypt("user2pwd");
                values.put("password", encryptedPassword);
                values.put("email", "user2@mail.ru");
                values.put("name", "User2");
                values.put("admin", 0);
                db.insert("users", null, values);
                values.remove("email");
                values.remove("name");
                values.remove("password");
                values.remove("admin");
            } catch (Exception e) {
                Log.e("Init", "ununable to init user 2 password");
            }

            try {
                String encryptedPassword = AESCrypt.encrypt("adminpwd");
                values.put("password", encryptedPassword);
                values.put("email", "admin@mail.ru");
                values.put("name", "Andmin");
                values.put("admin", 1);
                db.insert("users", null, values);
                values.remove("email");
                values.remove("name");
                values.remove("password");
                values.remove("admin");
            } catch (Exception e) {
                Log.e("Init", "ununable to init user 2 password");
            }

            Log.v("After users in", "Users incerted");

            Log.v("Before first date", "First day setting");
            long inDay = LocalDateTime.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
                    calendar.get(Calendar.DAY_OF_MONTH),
                    12,
                    0
            ).atZone(ZoneId.systemDefault()).toInstant().getEpochSecond();
            values.put("inDay", inDay);

            calendar.add(Calendar.DAY_OF_MONTH, 1);

            Log.v("Before second date", "Second day setting");
            long outDay = LocalDateTime.of(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH) + 1,
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
}

