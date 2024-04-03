package com.example.hotelbooking;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class UserState {
    private static UserState instance;

    private UserState(){}
    private DbHelper dbHelper;
    private String password;
    private String email;
    private String name;

    public static UserState getInstance() {
        if (instance == null) {
            instance = new UserState();
        }
        return instance;
    }

//    private static final String CREATE_TABLE_USERS = "CREATE TABLE users (" +
//            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "email TEXT NOT NULL UNIQUE," +
//            "name TEXT NOT NULL," +
//            "password TEXT NOT NULL," +
//            "admin INTEGER NOT NULL" +
//            ")";


    public String Register(String login, String name, String password, DbHelper dbHelper) {
        Log.v("UserState.Register", "Method reached");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Log.v("UserState.Register", "Writable db get");
        ContentValues values = new ContentValues();
        values.put("email", login);
        values.put("name", name);
        values.put("password", password);
        values.put("admin", 0);
        try {
            long newRowId = db.insertOrThrow("users", null, values);
            Log.v("UserState.Register", "Users sucessfuly inserted");
        } catch (SQLiteConstraintException e) {
            Log.w("UserState.Register", "Такой пользователь уже существует", e);
            return "loginExists";
        } catch (SQLException e) {
            Log.w("UserState.Register", "Ошибка при вставке данных", e);
            return "SQLException";
        } finally {
            values.remove("email");
            values.remove("name");
            values.remove("password");
            values.remove("admin");
            db.close();
        }
        return "";
    }

    public String Login(String login, String password) {

        return "";
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
