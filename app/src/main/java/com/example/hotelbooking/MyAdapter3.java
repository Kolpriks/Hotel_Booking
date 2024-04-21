package com.example.hotelbooking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {
    private OnRoomClickListener listener;
    private ArrayList<HotelRoom> rooms;
    private Context context;

    public MyAdapter3(Context context, ArrayList<HotelRoom> rooms, OnRoomClickListener listener) {
        this.context = context;
        this.rooms = rooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_broni, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelRoom currentRoom = getRoom(position);
        holder.textViewCity.setText(currentRoom.getCity());
        holder.imageView.setImageBitmap(currentRoom.getImgBitmap());
        holder.textViewGuests.setText("Гости: " + currentRoom.getPlaces()); // Updated to include prefix as per your layout
        holder.buttonBook.setOnClickListener(v -> {
            if (listener != null) {
                if (listener.onRoomClick(currentRoom)) {
                    rooms.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, rooms.size());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public HotelRoom getRoom(int position) {
        return rooms.get(position % rooms.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCity;
        public ImageView imageView; // Assuming the ImageView to display image from your XML
        public TextView textViewGuests; // The TextView for guests info
        public AppCompatButton buttonBook; // Button for booking or other actions

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCity = itemView.findViewById(R.id.textViewCity);
            imageView = itemView.findViewById(R.id.imageView5); // Adjusted ID to match your layout
            textViewGuests = itemView.findViewById(R.id.textView2); // Adjusted ID to match your layout
            buttonBook = itemView.findViewById(R.id.buttonBook);
        }
    }
}
