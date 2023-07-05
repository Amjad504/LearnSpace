package com.example.learnspace;

import android.graphics.drawable.Drawable;

public class Room_info {
    String roomName;
    Drawable image;

    String C_Id;

    public Room_info(String roomName, Drawable image, String c_Id) {
        this.roomName = roomName;
        this.image = image;
        C_Id = c_Id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getC_Id() {
        return C_Id;
    }

    public void setC_Id(String c_Id) {
        C_Id = c_Id;
    }
}
