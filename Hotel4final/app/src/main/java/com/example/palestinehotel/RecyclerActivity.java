package com.example.palestinehotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private List<Room> roomsFromDB =new ArrayList();
    private Context context = this;
    private RequestQueue queue;
    int smoking;
    int singleDouble;
    int numberOfGusts;

    ArrayList<String> urlss=new ArrayList<>();
    ArrayList<String> captionss= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        intent export data
        Intent intent= getIntent();
        smoking = intent.getIntExtra("smoking",0);
        singleDouble = intent.getIntExtra("double",0);
        numberOfGusts = intent.getIntExtra("numberOfGusts",2);
        Log.d("Error1", smoking+"  mm");


        getFromDB1 ();
    }


    public void getFromDB1(){
        //info_room_json.php

//            Intent intent  = new Intent(this, SearchActivity.class);
        queue = Volley.newRequestQueue(this);
        RecyclerView recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_room_json.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", response.length()+"  length");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                    if (obj.getInt("RoomType")==singleDouble && obj.getInt("MaximumOccupancy")>=numberOfGusts && obj.getInt("Smoking")==smoking){
                            roomsFromDB.add(new Room(obj.getInt("RoomNumber"),
                                    obj.getInt("RoomType"),
                                    obj.getInt("StandardOccupancy"),
                                    obj.getInt("MaximumOccupancy"),
                                    (float) obj.getDouble("BasePrice"),
                                    obj.getInt("ExtraPerson"),
                                    obj.getInt("HasJacuzzi"),
                                    obj.getInt("Smoking")
                            ));


                        int  s =obj.getInt("RoomNumber");
                        urlss.add("http://10.0.2.2:80/project2/images/"+s+".jpg");
                        captionss.add("Base Price:"+roomsFromDB.get(roomsFromDB.size()-1).getBasePrice()+
                                "\n Pay For Extra Person:"+roomsFromDB.get(roomsFromDB.size()-1).getExtraPerson()+
                                "\n Standard Occupancy:"+roomsFromDB.get(roomsFromDB.size()-1).getStandardOcupancy()+
                                "\n Maximum Occupancy:"+roomsFromDB.get(roomsFromDB.size()-1).getMaxOcupancy()
                        );

                        }


                        Log.d("Error8", "The size is: "+roomsFromDB.size()+"<<>> "+obj.getInt("RoomNumber"));
                    }catch(JSONException exception){
                        Log.d("Error7", "The size is: mnmn");

                    }
                }
                Log.d("Error6", "  "+urlss.size()+" urlss"+"The size is: "+roomsFromDB.size()+" response length is: "+response.length()+"\n"+roomsFromDB.toString());
                Toast.makeText(context, roomsFromDB.size()+"",
                        Toast.LENGTH_SHORT).show();

        recycler.setLayoutManager(new LinearLayoutManager(context));
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(context,urlss,captionss);
        recycler.setAdapter(adapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("Error1",  error.toString() +  error.toString());

            }
        });
        queue.add(request);
    }
}