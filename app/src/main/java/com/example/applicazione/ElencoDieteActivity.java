package com.example.applicazione;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ElencoDieteActivity extends AppCompatActivity {

    private static final String TAG = "ElencoDieteActivity";

    private FloatingActionButton fltABAddDieta;

    private ListView lv_carbo;

    DataBaseHelper dataBaseHelper;
    ArrayAdapter customerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_diete);

        initView();


        fltABAddDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElencoDieteActivity.this, AggiungiDietaActivity.class);
                ElencoDieteActivity.this.startActivity(intent);

                /*dataBaseHelper = new DataBaseHelper(ElencoDieteActivity.this);

                Cibo anacardi = new Cibo(1, "Anacardi", "Frutta secca", 10d, 11d, 12d, 13d, 14d, 15d, 16d, 17d, 18d);
                dataBaseHelper.addOne(anacardi);

                //customerArrayAdapter = new ArrayAdapter<Double>(ElencoDieteActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getCarbo("Anacardi"));
                //lv_carbo.setAdapter(customerArrayAdapter);

                Cibo cibo = dataBaseHelper.getCiboById(1);
                Toast.makeText(ElencoDieteActivity.this, "Cibo: " + cibo.toString(), Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    public void initView() {
        fltABAddDieta = findViewById(R.id.fltABAddDieta);
        lv_carbo = findViewById(R.id.lv_carbo);
    }
}