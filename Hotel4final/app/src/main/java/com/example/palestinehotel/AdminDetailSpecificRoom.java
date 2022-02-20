package com.example.palestinehotel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class AdminDetailSpecificRoom extends AppCompatActivity {
    //UI Views
    Button Back;

    private final int [] textureArrayWin={R.drawable.image1,R.drawable.image2,R.drawable.image3};
    private RequestQueue queue;
    private RecyclerView recycler;
    private ArrayList<Room> roomsFromDB =new ArrayList();
    public static Context context;

    private TextView Phone;
    private TextView State;
    private TextView Password;
    private TextView User_Name;
    private TextView Last_Name;
    private TextView availability;
    private TextView FirstName;
    private TextView Room_Number;
    private TextView Room_Type;
    private TextView standard_occupancy;
    private TextView maximum_occupancy;
    private TextView per_extra_person;
    private TextView Price;
    private TextView Guest_Id;
    private TextView smoking;
    private TextView Jacuzzi;
    private int GuestId;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private final String[] urls = new String[10] ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_detail_specific_room);

        Back=findViewById(R.id.Back);
        User_Name=findViewById(R.id.User_Name);
        Last_Name=findViewById(R.id.Last_Name);
        FirstName=findViewById(R.id.FirstName);
        Room_Number=findViewById(R.id.Room_Number);
        Room_Type=findViewById(R.id.Room_Type);
        standard_occupancy=findViewById(R.id.standard_occupancy);
        maximum_occupancy=findViewById(R.id.maximum_occupancy);
        per_extra_person=findViewById(R.id.per_extra_person);
        Price=findViewById(R.id.Price);
        Guest_Id=findViewById(R.id.Guest_Id);
        smoking=findViewById(R.id.smoking);
        Jacuzzi=findViewById(R.id.Jacuzzi);


        Intent receiveFromIntent =getIntent();
        String RoomNumber2 =receiveFromIntent.getStringExtra("RoomNumberAdmin");
//      Get GuestId from shared prefrences
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();
        GuestId = mPreferences.getInt("Id",0);

// Get the Room Number
        int RoomNumber3 = Integer.parseInt(RoomNumber2);
        Log.d("Error12", "the Room Number= "+RoomNumber3);
        Log.d("Error12", "the Room Number2= "+RoomNumber2);
        for (int i=0;i<10;i++){
            urls[i]="http://10.0.2.2:80/project2/images/"+RoomNumber3+"/"+i+".jpg";
        }

        Guest_Id.setText(Guest_Id.getText()+" "+GuestId);
        init();
        getFromDB1(RoomNumber3);
        getFromDB_GuestInfo();

    }


    private void getFromDB_GuestInfo(){
        //info_room_json.php
        queue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_userById_json.php?GuestId="+Guest_Id;
//        Log.d("Error1", "its here on DbB");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", "responseAdmin.length: "+response.length()+"");

                try {
                    JSONObject obj = response.getJSONObject(0);

                    Log.d("Error1", "response Admin ::: "+response.toString()+"");


//                    Room_Type.setText(Room_Type.getText()+" "+obj.getInt("RoomType"));
//                    Room_Number.setText(Room_Number.getText()+" "+obj.getInt("RoomNumber"));
//                    standard_occupancy.setText(standard_occupancy.getText()+" "+obj.getInt("StandardOccupancy"));
//                    maximum_occupancy.setText(maximum_occupancy.getText()+" "+obj.getInt("MaximumOccupancy"));
//                    Price.setText(Price.getText()+" "+obj.getDouble("BasePrice"));
//                    per_extra_person.setText(per_extra_person.getText()+" "+obj.getInt("ExtraPerson"));
//                    Jacuzzi.setText(Jacuzzi.getText()+" "+obj.getInt("HasJacuzzi"));
//                    smoking.setText(smoking.getText()+" "+obj.getInt("Smoking"));

//
//                        obj.getInt("RoomType");
//                        obj.getInt("StandardOccupancy");
//                        obj.getInt("MaximumOccupancy");
//                        (float) obj.getDouble("BasePrice");
//                        obj.getInt("ExtraPerson");
//                        obj.getInt("HasJacuzzi");
//                        obj.getInt("Smoking");

//                        urls.add("http://10.0.2.2:80/project2/images/"+i+".jpg");
//                        captionsArr.add("Base Price:"+roomsFromDB.get(i).getBasePrice()+
//                                "\n Pay For Extra Person:"+roomsFromDB.get(i).getExtraPerson()+
//                                "\n Standard Occupancy:"+roomsFromDB.get(i).getStandardOcupancy()+
//                                "\n Maximum Occupancy:"+roomsFromDB.get(i).getMaxOcupancy()
//                        );

                }catch(JSONException exception){
                    Log.d("Error", exception.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error22", error.toString());
//                Toast.makeText(context, error.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }




    private void getFromDB1(int RoomNumber3){
        //info_room_json.php
        queue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_roomSpecific_json.php?RoomNum="+RoomNumber3;
        Log.d("Error1", "its here on DbB");
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", "response.length: "+response.length()+"");

                    try {
                        JSONObject obj = response.getJSONObject(0);

                        if(obj.getInt("RoomType") == 1){
                            Room_Type.setText(Room_Type.getText()+" Double");
                        }else if(obj.getInt("RoomType") == 2)
                            Room_Type.setText(Room_Type.getText()+" Solo");
                        else
                            Room_Type.setText(Room_Type.getText()+" Single");

                        if(obj.getInt("HasJacuzzi") == 1){
                            Jacuzzi.setText(Jacuzzi.getText()+" Has jacuzzi");
                        }else
                            Jacuzzi.setText(Jacuzzi.getText()+" Doesn't has jacuzzi");

                        if(obj.getInt("Smoking") == 1){
                            smoking.setText(smoking.getText()+" Allowed");
                        }else
                            smoking.setText(smoking.getText()+" Not allowed");


                        Room_Number.setText(Room_Number.getText()+" "+obj.getInt("RoomNumber"));
                        standard_occupancy.setText(standard_occupancy.getText()+" "+obj.getInt("StandardOccupancy"));
                        maximum_occupancy.setText(maximum_occupancy.getText()+" "+obj.getInt("MaximumOccupancy"));
                        Price.setText(Price.getText()+" "+obj.getDouble("BasePrice"));
                        per_extra_person.setText(per_extra_person.getText()+" "+obj.getInt("ExtraPerson"));



                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error22", error.toString());
//                Toast.makeText(context, error.toString(),
//                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }



    private void init() {

        mPager =findViewById(R.id.pager);
        CirclePageIndicator indicator = findViewById(R.id.indicator);

        mPager.setAdapter(new SlidingImage_Adapter(AdminDetailSpecificRoom.this,urls));
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;
//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = urls.length;
        // Auto start of viewpager
        final Handler handler = new Handler();
        Runnable jamal;
        new Thread(jamal =new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        }).start();
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(jamal,1000);
            }
        },1000, 1000);
        // Pager listener over indicator "on swipe by click"
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }





    public void btn_Click_BACK(View view) {
        Intent intent  = new Intent(this, AdminSearch.class);
        finish();
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, AdminSearch.class);
        finish();
        startActivity(intent);
    }




}