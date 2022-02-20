package com.example.palestinehotel;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Room {

    Context context;
    private RequestQueue queue;

    private String name;
    private int imageID;

    private int roomNumber=201;
    private int roomType=0;            //single=0  Double=1  suit=3
    private int standardOcupancy;
    private int maxOcupancy;
    private float basePrice;
    private int extraPerson;

    private int HasJacuzzi;
    private int allowSmokong;




    public Room(int roomNumber, int roomType, int standardOcupancy, int maxOcupancy, float basePrice, int extraPerson, int hasJacuzzi, int allowSmokong) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.standardOcupancy = standardOcupancy;
        this.maxOcupancy = maxOcupancy;
        this.basePrice = basePrice;
        this.extraPerson = extraPerson;
        this.HasJacuzzi = hasJacuzzi;
        this.allowSmokong = allowSmokong;
    }

    public Room(String name, int imageID){
        this.name = name;
        this.imageID = imageID;
    }

    public Room() {
    }

    public String getName(){return name;}

    public int getImageID(){return imageID;}

    public int getRoomNumber() {return roomNumber;}

    public void setRoomNumber(int roomNumber) {this.roomNumber = roomNumber;}

    public int getRoomType() { return roomType;}

    public void setRoomType(int roomType) {this.roomType = roomType;}

    public int getStandardOcupancy() {return standardOcupancy;}

    public void setStandardOcupancy(int standardOcupancy) {this.standardOcupancy = standardOcupancy;}

    public int getMaxOcupancy() {return maxOcupancy;}

    public void setMaxOcupancy(int maxOcupancy) {this.maxOcupancy = maxOcupancy;}

    public float getBasePrice() {return basePrice;}

    public void setBasePrice(float basePrice) {this.basePrice = basePrice;}

    public int getExtraPerson() {return extraPerson;}

    public void setExtraPerson(int extraPerson) {this.extraPerson = extraPerson;}

    public int getHasJacuzzi() {return HasJacuzzi;}

    public void setHasJacuzzi(int hasJacuzzi) {HasJacuzzi = hasJacuzzi;}

    public int getAllowSmokong() {return allowSmokong;}

    public void setAllowSmokong(int allowSmokong) {this.allowSmokong = allowSmokong;}


    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", imageID=" + imageID +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", standardOcupancy=" + standardOcupancy +
                ", maxOcupancy=" + maxOcupancy +
                ", basePrice=" + basePrice +
                ", extraPerson=" + extraPerson +
                ", HasJacuzzi=" + HasJacuzzi +
                ", allowSmokong=" + allowSmokong +
                '}';
    }
}
