package com.example.palestinehotel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
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
import java.util.Calendar;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Context context=this;
    int singleDouble;
    int smoking;
    int selectedSpinn1;
    int selectedSpinn2;
    Spinner spnr1;
    Spinner spnr2;

    private long backPressedTime;
    private Toast backToast;
    private Button but_check_in;
    private Button but_check_Out;

    private ArrayList<String> urls=new ArrayList<>();
    private ArrayList<String> captionsArr=new ArrayList<>();
    private ArrayList<Room> roomsFromDB =new ArrayList();

    private RequestQueue queue;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        spnr1 = findViewById(R.id.spinner1);
        spnr2 = findViewById(R.id.spinner2);
        final Switch switch1 = findViewById(R.id.switch1);
        final Switch switch2 = findViewById(R.id.switch2);

//        dateText = findViewById(R.id.date_text);
        but_check_in = findViewById(R.id.show_dialog_chIn);
        but_check_Out = findViewById(R.id.show_dialog_chOut);

//        queue = Volley.newRequestQueue(this);
//        recycler = findViewById(R.id.ResultsRooms);

        fillSpinner1();
        fillSpinner2(2);

// Listener for spinner total num of person
        spnr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedSpinn1String=spnr1.getItemAtPosition(position).toString();
                if (selectedSpinn1String != null) {
                    selectedSpinn1=Integer.parseInt(selectedSpinn1String);
                    fillSpinner2(selectedSpinn1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
// Listener for spinner children
        spnr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinn2String =spnr2.getItemAtPosition(position).toString();
                if (selectedSpinn2String != null) {
                    selectedSpinn2 = Integer.parseInt(selectedSpinn2String);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch1.isChecked()){
                    singleDouble=1;
                }
                else if(!switch1.isChecked())
                    singleDouble=0;
            }
        });

        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switch2.isChecked()){
                    smoking=1;
                }
                else if (!switch2.isChecked())
                    smoking=0;
            }
        });

/////////////////Date picker
//      listener on click date check-in....
        DatePickerDialog.OnDateSetListener datePikerListner1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                (month==month && day>day_out_--->Error)
//                (month !=month && month_in > month_out)
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                but_check_in.setText("check_in"+"\n"+date);
            }
        };

//      listener on click date check-out....
        DatePickerDialog.OnDateSetListener datePikerListner2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                but_check_Out.setText("check_out"+"\n"+date);
            }
        };

//    check_in Date showing dialog.
        findViewById(R.id.show_dialog_chIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        datePikerListner1,
                        Calendar.getInstance().get(Calendar.YEAR),//here showing the instance date on the dialog
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

//    check_out Date showing dialog.
        findViewById(R.id.show_dialog_chOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(
                        context,
                        datePikerListner2,
                        Calendar.getInstance().get(Calendar.YEAR),//here showing the instance date on the dialog
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                datePickerDialog2.show();
            }
        });
/////////////////Date picker

    }


    private void fillSpinner1() {
        Spinner spnr1 = findViewById(R.id.spinner1);

        List<Integer> numAdults = new ArrayList<>();


        for (int i=1;i<=8;i++){
            numAdults.add(i);
        }

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numAdults);
        spnr1.setAdapter(adapter);

    }

    private void fillSpinner2(int selectedSpin1) {
        spnr1 = findViewById(R.id.spinner1);

        List<Integer> numChild = new ArrayList<>();
        // to fill the number of children
        if (selectedSpin1<=2){
            numChild.add(0);
            numChild.add(1);
        }
        else if (selectedSpin1>2 && selectedSpin1<=4){
            numChild.add(0);
            numChild.add(1);
            numChild.add(2);
        }
        else if (selectedSpin1>4 && selectedSpin1<=8){
            numChild.add(0);
            numChild.add(1);
            numChild.add(2);
            numChild.add(3);
            numChild.add(4);
        }



        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, numChild);
        spnr2.setAdapter(adapter);

    }

    public void onClickSearch(View view) {

        Intent intent  = new Intent(this, RecyclerActivity.class);
        intent.putExtra("smoking",smoking);
        intent.putExtra("double",singleDouble);
        intent.putExtra("numberOfGusts",selectedSpinn1);
        startActivity(intent);


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

    public void btn_Click_menu(View view) {
        Intent intent  = new Intent(this, listview.class);
        finish();
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    }










}