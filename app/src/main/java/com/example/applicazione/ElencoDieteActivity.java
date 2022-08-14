package com.example.applicazione;

import static com.example.applicazione.AggiungiDietaActivity.DIETA_NOME;
import static com.example.applicazione.AggiungiDietaActivity.ELENCO_DIETE;
import static com.example.applicazione.VisualizzaDietaActivity.DIETA_ID_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
    private TextView txtEmptyDiete;

    private RecyclerView dieteRecView;
    private DieteRecViewAdapter adapter;
    private ArrayList<Dieta> diete = new ArrayList<>();

    private String nomeDieta;

    private DataBaseDieta dataBaseDieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_diete);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        initView();

        dataBaseDieta = new DataBaseDieta(ElencoDieteActivity.this);

        diete = dataBaseDieta.getAllDiete();

        adapter.setDiete(diete);

        if (adapter.getItemCount() == 0) {
            txtEmptyDiete.setVisibility(View.VISIBLE);
        }

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

                            Intent intent = new Intent(ElencoDieteActivity.this, AggiungiDietaActivity.class);
                            intent.putExtra(DIETA_NOME, nomeDieta);
                            intent.putExtra(ELENCO_DIETE, 1);
                            intent.putExtra(DIETA_ID_KEY, -2);
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
        txtEmptyDiete = findViewById(R.id.txtEmptyDiete);

        adapter = new DieteRecViewAdapter(this);
        dieteRecView = findViewById(R.id.dieteRecView);
        fltABAddDieta = findViewById(R.id.fltABAddDieta);

        dieteRecView.setAdapter(adapter);
        dieteRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ElencoDieteActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ElencoDieteActivity.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}