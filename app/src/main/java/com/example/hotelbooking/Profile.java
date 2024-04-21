package com.example.hotelbooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Profile extends AppCompatActivity {
    private AppCompatButton button;
    private TextView username;
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new DbHelper(Profile.this);

        button = findViewById(R.id.appCompatButton);
        username = findViewById(R.id.textViewUsername);

        AppCompatButton editHotelsButton = findViewById(R.id.editHotelsButton);

        // Устанавливаем текст и обработчик для кнопки выхода/входа
        if (UserState.getInstance().isLoggedIn()) {
            username.setText(UserState.getInstance().getName());
            button.setText("Выйти");
        }
        Log.v("НЕ РАБОТАЕТ", UserState.getInstance().getAdmin() + "");
        // Проверяем, является ли пользователь администратором
        if (UserState.getInstance().isAdmin()) {
            editHotelsButton.setVisibility(View.VISIBLE);  // Делаем кнопку видимой
            editHotelsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Переход на активность администратора
                    Intent intent = new Intent(Profile.this, AdminActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            editHotelsButton.setVisibility(View.GONE);
        }

    }

    public void goToAdminActivity(View view) {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
    }


    /*
    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE reservations (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "inDay INTEGER," +
            "outDay INTEGER," +
            "roomId INTEGER," +
            "userId INTEGER," +
            // Foreign Key Constraint to ensure integrity
            "FOREIGN KEY(roomId) REFERENCES room(id)," +
            "FOREIGN KEY(userId) REFERENCES users(id)" +
            ")";

    public void loadReservations() {
        if (dbHelper == null) {
            dbHelper = new DbHelper(Profile.this);
        }
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String sql = "SELECT * " +
                "FROM reservations " +
                "WHERE userId = ? ";
        Cursor cursor = cursor = db.rawQuery(sql, new String[] {String.valueOf(UserState.getInstance().getId())});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int numberIndex = cursor.getColumnIndex("id");

                if (numberIndex != -1) {
                    result.add(new HotelRoom(cursor.getInt(numberIndex),
                            cursor.getString(cityIdIndex),
                            cursor.getInt(placesIndex),
                            cursor.getInt(imgIdIndex)));
                }
            } while (cursor.moveToNext());
            cursor.close();
            return result;
        }
        return null;
    }
*/
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