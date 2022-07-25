package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private CardView cvPalestra;
    private CardView cvDieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        cvPalestra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Cliccato", Toast.LENGTH_SHORT).show();
            }
        });

        cvDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Cliccato Dieta", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(){
        cvPalestra = findViewById(R.id.cvPalestra);
        cvDieta = findViewById(R.id.cvDieta);
    }
}

