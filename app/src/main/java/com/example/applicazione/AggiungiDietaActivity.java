package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

public class AggiungiDietaActivity extends AppCompatActivity {
    private Spinner spnCategoria;
    private Button btnScegli;
    private RecyclerView cibiRecView;
    private CibiRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_dieta);

        initView();
    }

    public void initView() {
        //creo e inizializzo tutti gli elementi dell'Activity
        adapter = new CibiRecViewAdapter(this);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnScegli = findViewById(R.id.btnScegli);
        cibiRecView = findViewById(R.id.cibiRecView);

        cibiRecView.setAdapter(adapter);
        cibiRecView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}