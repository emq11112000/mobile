package com.example.palestinehotel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AdminSearch extends AppCompatActivity {

    private Button SEARCH;
    private EditText roomNum;
    private ArrayList<Room> roomsFromDB =new ArrayList();
     private Room roomsFromDBArray [];
    private String urlsArray [];
    private Context context = this;
    private RequestQueue queue;
    private RecyclerView recycler;
    private long backPressedTime;
    private Toast backToast;
     private ArrayList<String> urls=new ArrayList<>();
    private ArrayList<String> captionsArr=new ArrayList<>();

    @Override
    protected void onStart() {
        getFromDB1();
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_search);
        SEARCH=findViewById(R.id.SEARCH);
        roomNum=findViewById(R.id.num);

    }

    public void btn_Click_searh(View view) {
        Intent intent  = new Intent(this, AdminDetailSpecificRoom.class);
String RoomNumber=roomNum.getText().toString()+"";
if ( RoomNumber!= null) {
    intent.putExtra("RoomNumberAdmin", (RoomNumber));
}
        finish();
        startActivity(intent);
    }

    public void btn_Click_menu(View view) {
        Intent intent  = new Intent(this, listviewAdmin.class);
        finish();
        startActivity(intent);
    }


    private void getFromDB1(){
        //info_room_json.php
        queue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_room_json.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", "response.length: "+response.length()+"");

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

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
                        urls.add("http://10.0.2.2:80/project2/images/"+s+".jpg");
                        captionsArr.add("Base Price:"+roomsFromDB.get(i).getBasePrice()+
                                "\n Pay For Extra Person:"+roomsFromDB.get(i).getExtraPerson()+
                                "\n Standard Occupancy:"+roomsFromDB.get(i).getStandardOcupancy()+
                                "\n Maximum Occupancy:"+roomsFromDB.get(i).getMaxOcupancy()
                        );

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

                Toast.makeText(context, roomsFromDB.size()+"",
                        Toast.LENGTH_SHORT).show();
                recycler.setLayoutManager(new LinearLayoutManager(context));
                CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(context,urls,captionsArr );
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);


    }



    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            moveTaskToBack(true);
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }

}