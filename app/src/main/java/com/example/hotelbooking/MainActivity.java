package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbHelper dbHelper = new DbHelper(this);

        // В фоновом потоке выполняем операцию добавления записи в БД
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Получаем объект SQLiteDatabase для записи в базу данных
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                dbHelper.onUpgrade(db, 1, 1);

                try {
                    // Здесь ваш код для взаимодействия с БД, например, добавление записи
                    ContentValues values = new ContentValues();
                    values.put("name", "Некий город");
                    db.insert("city", null, values);
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