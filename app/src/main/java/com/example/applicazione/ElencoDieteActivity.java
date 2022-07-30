package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ElencoDieteActivity extends AppCompatActivity {

    private FloatingActionButton fltABAddDieta;

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
            }
        });
    }

    public void initView(){
        fltABAddDieta = findViewById(R.id.fltABAddDieta);
    }
}