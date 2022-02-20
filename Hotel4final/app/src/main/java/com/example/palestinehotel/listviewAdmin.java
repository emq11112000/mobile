package com.example.palestinehotel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class listviewAdmin extends AppCompatActivity {
    private ListView list;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        Intent intent  = new Intent(this, profile.class);
        Intent intent1  = new Intent(this, login.class);
        back = findViewById(R.id.back);
        list= (ListView)findViewById(R.id.list);
        ArrayList<String> ll=new ArrayList<>();
        ll.add("Profil");
        ll.add("Log Out");
        ArrayAdapter adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,ll);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0){
                    finish();
                    startActivity(intent);
                }else if (i==1){
                    finish();
                    startActivity(intent1);
                }

            }
        });

    }

    public void btn_Click_back(View view) {
        Intent intent  = new Intent(this, AdminSearch.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, AdminSearch.class);
        finish();
        startActivity(intent);
    }
}