package com.example.palestinehotel;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CashOrCard extends AppCompatActivity {

    private Button card;
    private Button cash ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_or_card);
        card= findViewById(R.id.card);
        cash= findViewById(R.id.cash);
    }

    public void btn_Click_Cash(View view) {
        Intent intent  = new Intent(this, SearchActivity.class);
        finish();
        startActivity(intent);
    }

    public void btn_Click_Card(View view) {
        Intent intent  = new Intent(this, CardinfoActivity.class);
        finish();
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent  = new Intent(this, DetailSpecificRoom.class);
        finish();
        startActivity(intent);
    }
}
