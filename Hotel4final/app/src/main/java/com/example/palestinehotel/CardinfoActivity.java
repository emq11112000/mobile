package com.example.palestinehotel;

import android.content.Intent;
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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CardinfoActivity extends AppCompatActivity {
    private EditText cardnum ;
    private EditText date ;
    private EditText cvv ;
    private EditText postal ;
    private EditText countrycode ;
    private EditText mobile ;
    private Button book_now;

    private String cardnumString ;
    private String dateString ;
    private String cvvString ;
    private String postalString ;
    private String countrycodeString ;
    private String mobileString ;
    private int GuestId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardinfo);

        cardnum = findViewById(R.id.t1);
        date = findViewById(R.id.t2);
        cvv = findViewById(R.id.t3);
        postal = findViewById(R.id.t4);
        countrycode = findViewById(R.id.t5);
        mobile = findViewById(R.id.t6);

        book_now =findViewById(R.id.b1);


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mPreferences.edit();
        GuestId = mPreferences.getInt("Id",0);

        Toast.makeText(CardinfoActivity.this, GuestId+"" , Toast.LENGTH_SHORT).show();

    }

    public void btn_Click_book(View view) {

        Intent intent  = new Intent(this, SearchActivity.class);

        cardnumString =cardnum.getText().toString();
         dateString =date.getText().toString();
        cvvString =cvv.getText().toString();
         postalString =postal.getText().toString();
        countrycodeString =countrycode.getText().toString();
         mobileString =mobile.getText().toString();


        payByCard ( cardnumString,GuestId,  dateString,  cvvString, postalString, countrycodeString, mobileString);
        finish();
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, CashOrCard.class);
        finish();
        startActivity(intent);
    }



    private void payByCard (String cardnumString,int GuestId, String dateString, String cvvString,String postalString,String countrycodeString,String mobileString){

        String url = "http://10.0.2.2:80/project2/reserve_card_json.php";
        RequestQueue queue = Volley.newRequestQueue(CardinfoActivity.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(CardinfoActivity.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(CardinfoActivity.this,
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
                params.put("Cardnum", cardnumString);
                params.put("GuestId", GuestId+"");
                params.put("ExpirationDate", dateString);
                params.put("CVV", cvvString);
                params.put("PostalCode", postalString);
                params.put("CountryCode", countrycodeString);
                params.put("MobileNumber", mobileString);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }
}