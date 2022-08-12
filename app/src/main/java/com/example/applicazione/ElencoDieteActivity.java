package com.example.applicazione;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class ElencoDieteActivity extends AppCompatActivity {

    private static final String TAG = "ElencoDieteActivity";

    private FloatingActionButton fltABAddDieta;

    private RecyclerView dieteRecView;
    private DieteRecViewAdapter adapter;
    private ArrayList<Dieta> diete = new ArrayList<>();

    private String nomeDieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_diete);

        initView();

        diete.add(new Dieta(1, "Prova 1", 3));
        diete.add(new Dieta(2, "Prova 2", 5));

        adapter.setDiete(diete);

        fltABAddDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ElencoDieteActivity.this);
                builder.setMessage("Inserisci il nome della dieta: ");

                final EditText input = new EditText(ElencoDieteActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);

                builder.setPositiveButton("Avanti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ElencoDieteActivity.this, "Inserire il nome della dieta", Toast.LENGTH_SHORT).show();
                        } else {
                            nomeDieta = input.getText().toString();
                            Toast.makeText(ElencoDieteActivity.this, "Nome: " + nomeDieta, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ElencoDieteActivity.this, AggiungiDietaActivity.class);
                            ElencoDieteActivity.this.startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                });



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
        adapter = new DieteRecViewAdapter(this);
        dieteRecView = findViewById(R.id.dieteRecView);
        fltABAddDieta = findViewById(R.id.fltABAddDieta);

        dieteRecView.setAdapter(adapter);
        dieteRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}