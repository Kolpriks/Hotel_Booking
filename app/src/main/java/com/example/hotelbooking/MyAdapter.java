package com.example.hotelbooking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.Item;
import com.example.hotelbooking.R;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<HotelRoom> rooms;
    private Context context;


    public MyAdapter(Context context, ArrayList<HotelRoom> rooms) {
        this.context = context;
        this.rooms = rooms;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelRoom currentRoom = getRoom(position);

        holder.imageView.setImageResource(R.drawable.image1 + currentRoom.imgId - 1);
        holder.textView.setText(currentRoom.places + "");
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }


    public HotelRoom getRoom(int position) {
        return rooms.get(position % rooms.size());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
