package com.example.palestinehotel;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {
    private EditText Id;
    private EditText password;
    private EditText LastName;
    private EditText FirstName;
    private EditText Address;
    private EditText Phone;
    private EditText userName;
    private Button back;
    private Button edit;
    private Context context;

    private int GuestId;
    private ArrayList<Room> roomsFromDB =new ArrayList();
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Id=findViewById(R.id.id1);
        password=findViewById(R.id.Password);
        LastName=findViewById(R.id.lastname);
        FirstName=findViewById(R.id.firstname);
        Address=findViewById(R.id.address);
        Phone=findViewById(R.id.Phone);
        userName=findViewById(R.id.usered);

        back=findViewById(R.id.back);
        edit=findViewById(R.id.edit);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();

        GuestId = mPreferences.getInt("Id",1);
        Log.d("Error", "    "+GuestId);
//        getFromDB1();
        getFromDB122();
        Id.setText(GuestId+"");
    }

    public void edit(View view) {

        String passwords=password.getText().toString();
        String LastNames=LastName.getText().toString();
        String FirstNames=FirstName.getText().toString();
        String Addresss=Address.getText().toString();
        String Phones=Phone.getText().toString();
        String userNames=userName.getText().toString();
        UpdateUser(GuestId+"",FirstNames, LastNames, userNames, passwords,Addresss,Phones);
        Log.d("Error1","Done Updates!!!");

    }


    private void getFromDB122(){
        //info_room_json.php
        queue = Volley.newRequestQueue(this);
//        recycler = findViewById(R.id.ResultsRooms);
        String url = "http://10.0.2.2:80/project2/info_userById_json.php?GuestId="+GuestId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Error1", "response.length: "+response.length()+"");

                    try {
                        JSONObject obj = response.getJSONObject(0);
                        userName.setText(obj.getString("username"));
                        password.setText(obj.getString("password"));
                        LastName.setText(obj.getString("LastName"));
                        FirstName.setText(obj.getString("FirstName"));
                        Address.setText(obj.getString("State"));
                        Phone.setText(obj.getString("Phone"));


                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
//                }
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


    private void UpdateUser(String GuestId,String firstName, String lastName, String username,String password,String state,String phone){

        String url = "http://10.0.2.2:80/project2/update_user_json.php";
        RequestQueue queue = Volley.newRequestQueue(profile.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(profile.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(profile.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                //String firstName, String lastName, String username,String password,String State,int phone,int type

                params.put("GuestId", GuestId);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("username1", username);
                params.put("password1", password);
                params.put("state", state);
                params.put("phone", phone);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }



}

