package com.example.applicazione;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AggiungiDietaActivity extends AppCompatActivity {

    private static final String TAG = "AggiungiDietaActivity";

    private Spinner spnCategoria;
    private Button btnApplica;
    private RecyclerView cibiRecView;
    private CibiRecViewAdapter adapter;
    private ArrayList<Cibo> cibi;

    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_dieta);

        initView();

        dataBaseHelper = new DataBaseHelper(AggiungiDietaActivity.this);
        cibi = dataBaseHelper.getAllCibi();
        adapter.setCibi(cibi);

        btnApplica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cat = spnCategoria.getSelectedItem().toString();

                if (cat.equals("Tutte")) {
                    cibi = dataBaseHelper.getAllCibi();
                } else {
                    Log.d(TAG, "onClick: ECCOMI");
                    cibi = dataBaseHelper.getCibiByCat(cat);
                }

                adapter.setCibi(cibi);
                cibiRecView.setAdapter(adapter);
            }
        });
    }

    //creo e inizializzo tutti gli elementi dell'Activity
    public void initView() {
        adapter = new CibiRecViewAdapter(this);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnApplica = findViewById(R.id.btnApplica);
        cibiRecView = findViewById(R.id.cibiRecView);


        cibiRecView.setAdapter(adapter);
        cibiRecView.setLayoutManager(new GridLayoutManager(this, 2));


    }
}