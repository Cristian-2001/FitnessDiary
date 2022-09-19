package com.example.applicazione.dieta;

import static com.example.applicazione.dieta.AggiungiDietaActivity.DIETA_NOME;
import static com.example.applicazione.dieta.AggiungiDietaActivity.ELENCO_DIETE;
import static com.example.applicazione.dieta.VisualizzaDietaActivity.DIETA_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.applicazione.R;

import java.util.ArrayList;
import java.util.List;

public class ElencoCibiActivity extends AppCompatActivity {
    private RecyclerView cibiInsRecView;
    private CibiInsRecViewAdapter adapter;
    private TextView txtEmptyElenco;

    private List<Cibo> cibi = new ArrayList<>();

    private DataBaseCibo dataBaseCibo;

    //variabili ricevute da AggiungiDietaActivity
    private int dietaId, elencoDiete;
    private String nomeDieta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_cibi);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        dataBaseCibo = new DataBaseCibo(this);

        //ricevo le variabili da AggiungiDietaActivity
        Intent intent = getIntent();
        if(intent != null){
            dietaId = intent.getIntExtra(DIETA_ID_KEY, -1);
            elencoDiete = intent.getIntExtra(ELENCO_DIETE, -1);
            nomeDieta = intent.getStringExtra(DIETA_NOME);
        }

        cibi = dataBaseCibo.getInseriti();
        adapter.setCibi(cibi);

        if(adapter.getItemCount() == 0){
            txtEmptyElenco.setVisibility(View.VISIBLE);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        //chiamo AggiungiDietaAcitivity passandogli i parametri che aveva già in precedenza
        Intent intent1 = new Intent(ElencoCibiActivity.this, AggiungiDietaActivity.class);
        intent1.putExtra(DIETA_ID_KEY, dietaId);
        intent1.putExtra(ELENCO_DIETE, elencoDiete);
        intent1.putExtra(DIETA_NOME, nomeDieta);
        ElencoCibiActivity.this.startActivity(intent1);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ElencoCibiActivity.this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView(){
        adapter = new CibiInsRecViewAdapter(this);
        cibiInsRecView = findViewById(R.id.cibiInsRecView);
        txtEmptyElenco = findViewById(R.id.txtEmptyElenco);

        cibiInsRecView.setAdapter(adapter);
        cibiInsRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}