package com.example.applicazione;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

                Intent intent = new Intent(MainActivity.this, ElencoAllenamentiActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        cvDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Cliccato Dieta", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, ElencoDieteActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    private void initView(){
        cvPalestra = findViewById(R.id.cvPalestra);
        cvDieta = findViewById(R.id.cvDieta);
    }
}

