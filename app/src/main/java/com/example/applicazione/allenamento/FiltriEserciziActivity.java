package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ELENCO_ALL;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_DIFF_KEY;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_GRUPPOMUSC_KEY;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_MOD_KEY;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_NOME_KEY;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_PARTECORPO_KEY;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ES_TIPO_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.applicazione.MainActivity;
import com.example.applicazione.R;

public class FiltriEserciziActivity extends AppCompatActivity {
    private EditText edtTxtNomeA;
    private Spinner spnGruppoMusc;
    private Spinner spnDifficolta;
    private Spinner spnParteCorpo;
    private Spinner spnTipologia;
    private Spinner spnModalita;
    private Button btnApplicaA, btnAnnullaA;

    private String nome, gruppoMusc, difficolta, parteCorpo, tipologia, modalita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtri_esercizi);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        btnAnnullaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnApplicaA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = edtTxtNomeA.getText().toString();
                gruppoMusc = spnGruppoMusc.getSelectedItem().toString();
                difficolta = spnDifficolta.getSelectedItem().toString();
                parteCorpo = spnParteCorpo.getSelectedItem().toString();
                tipologia = spnTipologia.getSelectedItem().toString();
                modalita = spnModalita.getSelectedItem().toString();

                Intent intent = new Intent(FiltriEserciziActivity.this, AggiungiAllenamentoActivity.class);
                intent.putExtra(ES_NOME_KEY, nome);
                intent.putExtra(ES_GRUPPOMUSC_KEY, gruppoMusc);
                intent.putExtra(ES_DIFF_KEY, difficolta);
                intent.putExtra(ES_PARTECORPO_KEY, parteCorpo);
                intent.putExtra(ES_TIPO_KEY, tipologia);
                intent.putExtra(ES_MOD_KEY, modalita);

                //metto l'extra ELENCO_ALL uguale a 2 così so che devo applicare dei filtri
                intent.putExtra(ELENCO_ALL, 2);
                FiltriEserciziActivity.this.startActivity(intent);
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //this.finish();
                goBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /**
     * funzione per tornare indietro
     * faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
     */
    private void goBack() {
        finish();
    }

    private void initView() {
        edtTxtNomeA = findViewById(R.id.edtTxtNomeA);
        spnGruppoMusc = findViewById(R.id.spnGruppoMusc);
        spnDifficolta = findViewById(R.id.spnDifficolta);
        spnParteCorpo = findViewById(R.id.spnParteCorpo);
        spnTipologia = findViewById(R.id.spnTipologia);
        spnModalita = findViewById(R.id.spnModalita);
        btnApplicaA = findViewById(R.id.btnApplicaA);
        btnAnnullaA = findViewById(R.id.btnAnnullaA);
    }
}