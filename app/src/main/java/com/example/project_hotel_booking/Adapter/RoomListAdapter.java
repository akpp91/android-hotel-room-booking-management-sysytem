package com.example.project_hotel_booking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project_hotel_booking.R;
import com.example.project_hotel_booking.activity.Date_Activity;
import com.example.project_hotel_booking.entity.Room;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.RoomViewHolder>
{
    private List<Room> roomList;
    private Context context;
    private SharedPreferences sharedPreferences;

    public RoomListAdapter( Context context, List<Room> roomList) {

        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomListAdapter.RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.room_list, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomListAdapter.RoomViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.textRoomName.setText(""+room.getRoomNumber());
        holder.textDetails.setText(room.getRoomType());

        Glide.with(context).load("http://192.168.0.110:4004/"+room.getImages()).into(holder.image);
        holder.selectButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                // Save room details to SharedPreferences
                sharedPreferences = context.getSharedPreferences("project", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("selected_room_number", room.getRoomNumber());
                editor.putString("selected_room_type", room.getRoomType());
                editor.apply();

                Intent intent =new Intent( context, Date_Activity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView textRoomName;
        TextView textDetails;
        Button selectButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            textRoomName = itemView.findViewById(R.id.textRoomName);
            textDetails = itemView.findViewById(R.id.textDetails);
            selectButton = itemView.findViewById(R.id.selectRoom);
        }
    }
}
