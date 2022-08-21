package com.example.applicazione.allenamento;

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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
import com.example.applicazione.dieta.AggiungiDietaActivity;
import com.example.applicazione.dieta.DataBaseCibo;
import com.example.applicazione.dieta.DataBaseDieta;

import java.util.ArrayList;
import java.util.List;

public class AggiungiAllenamentoActivity extends AppCompatActivity {
    private static final String TAG = "AggiungiAllenamActivity";    //TAG per i log
    public static final String ALLENAMENTO_NOME = "allenamentoNome";
    public static final String ELENCO_ALL = "elencoAllenamenti";

    public static final String ES_NOME_KEY = "nomeEs";
    public static final String ES_GRUPPOMUSC_KEY = "gruppoMuscEs";
    public static final String ES_DIFF_KEY = "diffEs";
    public static final String ES_PARTECORPO_KEY = "parteCorpoEs";
    public static final String ES_TIPO_KEY = "tipoEs";
    public static final String ES_MOD_KEY = "modEs";

    //id dell'allenamento da modificare
    private static int allenamentoId;

    private static Allenamento allenamento = null;
    private static String nomeAll;
    private String nome, gruppoMusc, difficolta, parteCorpo, tipologia, modalita;
    private List<Esercizio> esercizi = new ArrayList<>();

    private DataBaseAllenamento dataBaseAllenamento;
    private DataBaseEsercizio dataBaseEsercizio;

    private TextView txtNumEs, txtEmptyEs;
    private Button btnFiltra, btnSalvaALl;

    private RecyclerView esRecView;
    private EserciziRecViewAdapter adapter;

    private static List<Integer> eserciziId = new ArrayList<>();
    private static List<Integer> eserciziSerie = new ArrayList<>();
    private static List<Integer> eserciziReps = new ArrayList<>();
    private static List<Integer> eserciziTRec = new ArrayList<>();
    private static int numElem = 0;

    //TODO finire questa activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_allenamento);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        dataBaseAllenamento = new DataBaseAllenamento(AggiungiAllenamentoActivity.this);
        dataBaseEsercizio = new DataBaseEsercizio(AggiungiAllenamentoActivity.this);
        esercizi = dataBaseEsercizio.getAllEsercizi();
        adapter.setEsercizi(esercizi);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getIntExtra(ELENCO_ALL, -1) == 1) {
                //sono arrivato dall'elenco degli allenamenti quindi resetto tutto
                nomeAll = intent.getStringExtra(ALLENAMENTO_NOME);
                eserciziId.clear();
                eserciziSerie.clear();
                eserciziReps.clear();
                eserciziTRec.clear();
                numElem = 0;
            } else if (intent.getIntExtra(ELENCO_ALL, -1) == 2) {
                //sono arrivato dai filtri quindi li applico
                nome = intent.getStringExtra(ES_NOME_KEY);
                gruppoMusc = intent.getStringExtra(ES_GRUPPOMUSC_KEY);
                difficolta = intent.getStringExtra(ES_DIFF_KEY);
                parteCorpo = intent.getStringExtra(ES_PARTECORPO_KEY);
                tipologia = intent.getStringExtra(ES_TIPO_KEY);
                modalita = intent.getStringExtra(ES_MOD_KEY);

                esercizi = dataBaseEsercizio.getEserciziFiltri(nome, gruppoMusc, difficolta, parteCorpo, tipologia, modalita);
                adapter.setEsercizi(esercizi);
            }
        }

        if (adapter.getItemCount() == 0) {
            txtEmptyEs.setVisibility(View.VISIBLE);
        } else {
            txtEmptyEs.setVisibility(View.GONE);
        }

        if (numElem == 1) {
            txtNumEs.setText(numElem + " elemento");
        } else {
            txtNumEs.setText(numElem + " elementi");
        }

        btnFiltra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AggiungiAllenamentoActivity.this, FiltriAllenamentiActivity.class);
                AggiungiAllenamentoActivity.this.startActivity(intent);
            }
        });

        btnSalvaALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numElem == 0) {
                    Toast.makeText(AggiungiAllenamentoActivity.this, "Inserire almeno un elemento", Toast.LENGTH_SHORT).show();
                } else {
                    allenamento = new Allenamento(nomeAll, eserciziId, eserciziSerie, eserciziReps, eserciziTRec, numElem);

                    dataBaseAllenamento.addOne(allenamento);
                }

                Intent intent1 = new Intent(AggiungiAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                //intent1.putExtra(ALL_ID_KEY, allenamento.getId());
                AggiungiAllenamentoActivity.this.startActivity(intent1);
            }
        });
    }

    private void initView() {
        /* inizializzo gli elementi dell'activity */
        txtNumEs = findViewById(R.id.txtNumEs);
        txtEmptyEs = findViewById(R.id.txtEmptyEs);
        btnFiltra = findViewById(R.id.btnFiltra);
        btnSalvaALl = findViewById(R.id.btnSalvaAll);

        adapter = new EserciziRecViewAdapter(this);
        esRecView = findViewById(R.id.esRecView);
        esRecView.setAdapter(adapter);
        esRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    //metodo per salvare i dati
    public static void modificaAllenamento(int esId, int serie, int reps, int rec) {
        eserciziId.add(esId);
        eserciziSerie.add(serie);
        eserciziReps.add(reps);
        eserciziTRec.add(rec);
        numElem++;
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(AggiungiAllenamentoActivity.this);
                builder.setMessage("L'allenamento corrente non verrà salvato. Vuoi tornare indietro?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AggiungiAllenamentoActivity.this.finish();
                        Intent intent = new Intent(AggiungiAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                        AggiungiAllenamentoActivity.this.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AggiungiAllenamentoActivity.this);
        builder.setMessage("L'allenamento corrente non verrà salvato. Vuoi tornare indietro?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AggiungiAllenamentoActivity.this.finish();
                Intent intent = new Intent(AggiungiAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Annulla", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}