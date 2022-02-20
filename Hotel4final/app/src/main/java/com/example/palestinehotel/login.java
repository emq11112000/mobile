package com.example.palestinehotel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class login extends AppCompatActivity {
    private EditText edtUser, pass;
    private RequestQueue queue;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
//    SharedPreferences.Editor editor2;
    private CheckBox box;
    Context context;
    //the variablies for the shared prefs
    public static final String Name ="Name";
    public static final String Name2 ="Name2";
    public static final String Pass ="Pass";
    public static final String Flag ="Flag";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        queue = Volley.newRequestQueue(this);
        edtUser = findViewById(R.id.edusername);
        pass = findViewById(R.id.edpassword);
        box = findViewById(R.id.checkbox);

        setUpPrefs();   //to set up a file and an editor for shared prefs
        checkPrefs();   //to check if there is prefs saved or no
        context= this;
    }


    private void checkPrefs(){
        boolean flag = sharedPref.getBoolean(Flag,false);
        if(flag){
            String name=sharedPref.getString(Name,"");
            String password=sharedPref.getString(Pass,"");
            edtUser.setText(name);
            pass.setText(password);
            box.setChecked(true);
        }

    }



    private void setUpPrefs(){
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPref.edit();

    }
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit The App").setMessage("Are you sure you want to exit?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context,"Enjoy our app",Toast.LENGTH_LONG).show();
            }
        }).show();
    }


    public void btn_Click_login(View view) {
        String usern = edtUser.getText().toString();
        String passw = pass.getText().toString();
        Intent intent  = new Intent(this, SearchActivity.class);
        Intent intentAdmin  = new Intent(this, AdminSearch.class);
        //sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
        //editor = sharedPref.edit();

        editor.apply();
        String url = "http://10.0.2.2:80/project2/info_user_json.php?usern=" + usern;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<User> logins = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);



                            logins.add(new User(obj.getString("username"), obj.getString("password"),obj.getInt("GuestId")));

                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }

                // check if username and password are true
                if ( logins.size()==1 && usern.equals(logins.get(0).getUsername()) && passw.equals(logins.get(0).getPassword()) ){
                    // if the username and password are correct and the checkbox is checked they will be saved in shared prefs
                    editor.putInt("Id",logins.get(0).getId());
                    editor.commit();


                    if(box.isChecked()){
                        editor.putString(Name,usern);
                        editor.putString(Pass,passw);
                        editor.putBoolean(Flag,true);
                        editor.commit();
                    }
                    finish();
                    if(CustomerOrAdmin( logins.get(0).getUsername()) ==1){
                        startActivity(intentAdmin);
                    }
                    else if(CustomerOrAdmin( logins.get(0).getUsername()) ==0){
                        startActivity(intent);
                    }

                }

                else
                    Toast.makeText(login.this, "Password or username is not valid",
                            Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(login.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
                Log.d("key",  error.toString() +  error.toString());
            }
        });
        queue.add(request);

    }


    public void onClickHaccent(View view) {
        Intent intent  = new Intent(this, signup.class);
        startActivity(intent);
    }

    private int CustomerOrAdmin(String username){
        String [] arr =username.split("@");
        if (arr.length==2 && arr[1].equals("customer")){
            return 0;
        }else if (arr.length==2 && arr[1].equals("admin")){
            return 1;
        }
        else
            return 2;  //is not valid username because of tail of username
    }

}