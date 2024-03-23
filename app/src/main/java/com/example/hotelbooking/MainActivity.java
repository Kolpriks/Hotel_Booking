package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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