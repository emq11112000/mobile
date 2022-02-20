package com.example.palestinehotel;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
public class DetailSpecificRoom extends AppCompatActivity {

    static int i=0;
    private final int [] textureArrayWin={R.drawable.image1,R.drawable.image2,R.drawable.image3};
    private RequestQueue queue;
    private RecyclerView recycler;
    private ArrayList<Room> roomsFromDB =new ArrayList();
    public static Context context;


    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private ArrayList<String> urls22=new ArrayList<>();
    private ArrayList<String> captionsArr=new ArrayList<>();
    private final String[] urls = new String[10] ;


    private TextView Room_Number;
    private TextView Room_Type;
    private TextView standard_occupancy;
    private TextView maximum_occupancy;
    private TextView per_extra_person;
    private TextView Price;
    private TextView smoking;
    private TextView Jacuzzi;
    private TextView Phone;
    private TextView State;
    private TextView Password;
    private TextView User_Name;
    private TextView Last_Name;
    private TextView FirstName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_specific_room);
        Intent receiveFromIntent =getIntent();
        String RoomNumber2 =receiveFromIntent.getStringExtra("RoomNumber");


        Room_Number=findViewById(R.id.textView9);
        Room_Type=findViewById(R.id.textView8);

        standard_occupancy=findViewById(R.id.textView7);
        maximum_occupancy=findViewById(R.id.textView6);
        per_extra_person=findViewById(R.id.textView4);
        Price=findViewById(R.id.textView5);
        smoking=findViewById(R.id.textView2);
        Jacuzzi=findViewById(R.id.textView3);

        int RoomNumber3 = Integer.parseInt(RoomNumber2);

        for (int i=0;i<10;i++){
            urls[i]="http://10.0.2.2:80/project2/images/"+RoomNumber3+"/"+i+".jpg";
        }

        getFromDB1(RoomNumber3);
        init();


    }


    private void getFromDB1(int RoomNumber3){
        //info_room_json.php
        queue = Volley.newRequestQueue(this);
        recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_roomSpecific_json.php?RoomNum="+RoomNumber3;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", "response.length: "+response.length()+"");

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);

                        if(obj.getInt("RoomType") == 1){
                            Room_Type.setText(Room_Type.getText()+" double");
                        }else
                            Room_Type.setText(Room_Type.getText()+" solo");

                        if(obj.getInt("HasJacuzzi") == 1){
                            Jacuzzi.setText(Jacuzzi.getText()+" has jacuzzi");
                        }else
                            Jacuzzi.setText(Jacuzzi.getText()+" doesn't has jacuzzi");

                        if(obj.getInt("Smoking") == 1){
                            smoking.setText(smoking.getText()+" allowed");
                        }else
                            smoking.setText(smoking.getText()+" not allowed");

                        Room_Number.setText(Room_Number.getText()+" "+obj.getInt("RoomNumber"));
                        standard_occupancy.setText(standard_occupancy.getText()+" "+obj.getInt("StandardOccupancy"));
                        maximum_occupancy.setText(maximum_occupancy.getText()+" "+obj.getInt("MaximumOccupancy"));
                        Price.setText(Price.getText()+" "+obj.getDouble("BasePrice"));
                        per_extra_person.setText(per_extra_person.getText()+" "+obj.getInt("ExtraPerson"));
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                Log.d("Error0", urls22.size()+" is size");

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



    public void btn_Click2(View view) {
        Intent intent  = new Intent(this, CashOrCard.class);
        finish();
        startActivity(intent);

    }



    private void init() {

        mPager =findViewById(R.id.pager);
        CirclePageIndicator indicator = findViewById(R.id.indicator);

        mPager.setAdapter(new SlidingImage_Adapter(DetailSpecificRoom.this,urls));
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
    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, RecyclerActivity.class);
        finish();
        startActivity(intent);
    }
}


//}