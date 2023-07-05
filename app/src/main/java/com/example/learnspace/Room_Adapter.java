package com.example.learnspace;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Room_Adapter extends RecyclerView.Adapter<Room_Adapter.MyViewHolder>{


    List<Room_info> ls;
    Context c;

    public Room_Adapter(List<Room_info> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @NonNull
    @Override
    public Room_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(c).inflate(R.layout.room_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull Room_Adapter.MyViewHolder holder, int position) {
        holder.room_name.setText(ls.get(position).getRoomName());
        String C_ID = ls.get(position).getC_Id();

        Room_info roomInfo = ls.get(position);
        Drawable imageDrawable = roomInfo.getImage();

        holder.room_pic.setImageDrawable(imageDrawable);

        holder.room_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, Meeting_Screen.class);
                i.putExtra("ID",C_ID);
                c.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public void reset(List<Room_info> filterlist) {

        this.ls = filterlist;
        notifyDataSetChanged();
    }

    public void setfilterList(List<Room_info> filterlist) {
        this.ls = filterlist;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView room_pic;
        TextView room_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            room_name = itemView.findViewById(R.id.room_row_name);
            room_pic = itemView.findViewById(R.id.room_pic);
        }
    }
}
