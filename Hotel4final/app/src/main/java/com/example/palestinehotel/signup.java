package com.example.palestinehotel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class signup extends AppCompatActivity {
    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText password;
    private EditText repassword;
    private EditText state;
    private EditText phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firstName = findViewById(R.id.editFirstName);
        lastName = findViewById(R.id.editLastName);
        username = findViewById(R.id.editUsername);
        password = findViewById(R.id.editPassword);
        state = findViewById(R.id.editState);
        phone = findViewById(R.id.editPhone);
        repassword =findViewById(R.id.rewritePassword);
    }

    public void btnOnclickSignUp(View view) {
        if(repassword.getText().equals(password.getText())){
            String firstNameString = firstName.getText().toString();
            String lastNameString = lastName.getText().toString();
            String usernameString = username.getText().toString();
            String passwordString = password.getText().toString();
            String stateString = state.getText().toString();
            String phoneString = phone.getText().toString();
            Intent intent  = new Intent(this, login.class);
            addUser(firstNameString,lastNameString,usernameString,passwordString,stateString,phoneString);
            startActivity(intent);
        }else{
            Toast.makeText(signup.this, "please enter the same password to confirm", Toast.LENGTH_SHORT).show();
        }





    }

    private void addUser(String firstName, String lastName, String username,String password,String state,String phone){

        String url = "http://10.0.2.2:80/project2/reserve_user_json.php";
        RequestQueue queue = Volley.newRequestQueue(signup.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(signup.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(signup.this,
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




    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, login.class);
        finish();
        startActivity(intent);
    }

}