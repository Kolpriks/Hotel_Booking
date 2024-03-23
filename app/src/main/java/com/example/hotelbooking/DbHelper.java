package com.example.hotelbooking;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hotelapp.db";
    private static final int DATABASE_VERSION = 1;

    // SQL statement to create a new table
    private static final String CREATE_TABLE_CITY = "CREATE TABLE city (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT" +
            ")";

    private static final String CREATE_TABLE_HOTEL = "CREATE TABLE hotel (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "cityId INTEGER," +
            "name TEXT," +
            // Foreign Key Constraint to ensure integrity
            "FOREIGN KEY(cityId) REFERENCES city(id)" +
            ")";

    private static final String CREATE_TABLE_ROOM = "CREATE TABLE room (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "hotelId INTEGER," +
            "places INTEGER," +
            // Foreign Key Constraint to ensure integrity
            "FOREIGN KEY(hotelId) REFERENCES hotel(id)" +
            ")";

    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE reservations (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "month TEXT," +
            "inDay INTEGER," +
            "outDay INTEGER," +
            "roomId INTEGER," +
            // Foreign Key Constraint to ensure integrity
            "FOREIGN KEY(roomId) REFERENCES room(id)" +
            ")";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute SQL statements to create multiple tables
        db.execSQL(CREATE_TABLE_CITY);
        db.execSQL(CREATE_TABLE_HOTEL);
        db.execSQL(CREATE_TABLE_ROOM);
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables if existed
        db.execSQL("DROP TABLE IF EXISTS city");
        db.execSQL("DROP TABLE IF EXISTS hotel");
        db.execSQL("DROP TABLE IF EXISTS room");
        db.execSQL("DROP TABLE IF EXISTS reservations");
        // Create tables again
        onCreate(db);
    }


    // Add your data access methods here
}