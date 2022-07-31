package com.example.applicazione;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ElencoDieteActivity extends AppCompatActivity {
    private static final String TAG = "ElencoDieteActivity";

    private FloatingActionButton fltABAddDieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_diete);

        initView();

        fltABAddDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(ElencoDieteActivity.this, AggiungiDietaActivity.class);
                ElencoDieteActivity.this.startActivity(intent);*/

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();

                String carbo = databaseAccess.getCarbo("Anacardi");
                Log.d(TAG, "onClick: Carboidrati:" + carbo);

                Toast.makeText(ElencoDieteActivity.this, "Carboidrati: " + carbo, Toast.LENGTH_SHORT).show();

                databaseAccess.close();
            }
        });
    }

    public void initView() {
        fltABAddDieta = findViewById(R.id.fltABAddDieta);
    }
}