package com.example.applicazione;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.applicazione.dieta.ElencoDieteActivity;

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

                Intent intent = new Intent(MainActivity.this, ElencoAllenamentiActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        cvDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ElencoDieteActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
    }


    private void initView(){
        cvPalestra = findViewById(R.id.cvPalestra);
        cvDieta = findViewById(R.id.cvDieta);
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        finish();

        /*Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
    }
}

