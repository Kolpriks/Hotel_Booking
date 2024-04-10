package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Profile extends AppCompatActivity {
    private AppCompatButton button;
    private TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        DbHelper dbHelper = new DbHelper(Profile.this);

        button = findViewById(R.id.appCompatButton);
        username = findViewById(R.id.textViewUsername);

        if (UserState.getInstance().isLoggedIn()) {
            username.setText(UserState.getInstance().getName());
            button.setText("Выйти");
        }
    }

    public void loadReservations() {

    }

    public void toLogin(View view){
        if (UserState.getInstance().isLoggedIn()) {
            UserState.getInstance().Logout();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

    public void backOnMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}