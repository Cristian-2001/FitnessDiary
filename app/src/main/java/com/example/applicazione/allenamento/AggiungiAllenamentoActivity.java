package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;

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
    private static int allenamentoId = -2;

    private static Allenamento allenamento = null;
    private static String nomeAll;
    private String nome, gruppoMusc, difficolta, parteCorpo, tipologia, modalita;
    private List<Esercizio> esercizi = new ArrayList<>();

    private DataBaseAllenamento dataBaseAllenamento;
    private DataBaseEsercizio dataBaseEsercizio;

    private TextView txtNumEs, txtEmptyEs;
    private EditText edtTxtNomeCercaEs;
    private Button btnSalvaALl;
    private ImageView btnFiltra, btnFiltraExp;

    private ConstraintLayout expandableConsLayout;

    private Spinner spnGruppoMusc;
    private Spinner spnDifficolta;
    private Spinner spnParteCorpo;
    private Spinner spnTipologia;
    private Spinner spnModalita;

    private RecyclerView esRecView;
    private EserciziRecViewAdapter adapter;

    private static List<Integer> eserciziId = new ArrayList<>();
    private static List<Integer> eserciziSerie = new ArrayList<>();
    private static List<Integer> eserciziReps = new ArrayList<>();
    private static List<Integer> eserciziTRec = new ArrayList<>();
    private static int numElem = 0;

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

            if (intent.getIntExtra(ALLENAMENTO_ID_KEY, -1) > -1) {
                allenamentoId = intent.getIntExtra(ALLENAMENTO_ID_KEY, -1);
                Log.d(TAG, "onCreate: ID: " + allenamentoId);
                nomeAll = dataBaseAllenamento.getAllenamentoById(allenamentoId).getNome();
                eserciziId = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziId();
                eserciziSerie = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziSerie();
                eserciziReps = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziReps();
                eserciziTRec = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziTrec();
                numElem = dataBaseAllenamento.getAllenamentoById(allenamentoId).getNumElem();
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
                btnFiltraExp.setVisibility(View.VISIBLE);
                expandableConsLayout.setVisibility(View.VISIBLE);
                btnFiltra.setVisibility(View.GONE);
                /*Intent intent = new Intent(AggiungiAllenamentoActivity.this, FiltriEserciziActivity.class);
                AggiungiAllenamentoActivity.this.startActivity(intent);*/
            }
        });

        btnFiltraExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnFiltraExp.setVisibility(View.GONE);
                expandableConsLayout.setVisibility(View.GONE);
                btnFiltra.setVisibility(View.VISIBLE);
            }
        });

        btnSalvaALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numElem == 0) {
                    Toast.makeText(AggiungiAllenamentoActivity.this, "Inserire almeno un elemento", Toast.LENGTH_SHORT).show();
                } else {

                    allenamento = new Allenamento(nomeAll, eserciziId, eserciziSerie, eserciziReps, eserciziTRec, numElem);

                    if (allenamentoId > -1) {
                        Log.d(TAG, "onClick: ECCOMI");
                        dataBaseAllenamento.modificaAllenamento(allenamentoId, allenamento);
                    } else {
                        Log.d(TAG, "onClick: SONO QUI");
                        dataBaseAllenamento.addOne(allenamento);
                    }

                }

                Intent intent1 = new Intent(AggiungiAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                intent1.putExtra(ALLENAMENTO_ID_KEY, allenamento.getId());
                AggiungiAllenamentoActivity.this.startActivity(intent1);
            }
        });

        edtTxtNomeCercaEs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cercaEsercizi();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //do nothing
            }
        });

        spnGruppoMusc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cercaEsercizi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        spnDifficolta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cercaEsercizi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        spnParteCorpo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cercaEsercizi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        spnTipologia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cercaEsercizi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });

        spnModalita.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cercaEsercizi();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //do nothing
            }
        });
    }

    private void initView() {
        /* inizializzo gli elementi dell'activity */
        txtNumEs = findViewById(R.id.txtNumEs);
        txtEmptyEs = findViewById(R.id.txtEmptyEs);
        edtTxtNomeCercaEs = findViewById(R.id.edtTxtNomeCercaEs);
        btnFiltra = findViewById(R.id.btnFiltra);
        btnFiltraExp = findViewById(R.id.btnFiltraExp);
        btnSalvaALl = findViewById(R.id.btnSalvaAll);

        adapter = new EserciziRecViewAdapter(this);
        esRecView = findViewById(R.id.esRecView);
        esRecView.setAdapter(adapter);
        esRecView.setLayoutManager(new LinearLayoutManager(this));

        expandableConsLayout = findViewById(R.id.expandableConsLayout);

        spnGruppoMusc = findViewById(R.id.spnGruppoMusc);
        spnDifficolta = findViewById(R.id.spnDifficolta);
        spnParteCorpo = findViewById(R.id.spnParteCorpo);
        spnTipologia = findViewById(R.id.spnTipologia);
        spnModalita = findViewById(R.id.spnModalita);
    }

    /**
     * metodo per salvare i dati
     */
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

    /**
     * cerca gli esercizi con i criteri inseriti e aggiorna l'elenco
     */
    private void cercaEsercizi() {
        txtEmptyEs.setVisibility(View.GONE);

        nome = edtTxtNomeCercaEs.getText().toString();
        gruppoMusc = spnGruppoMusc.getSelectedItem().toString();
        difficolta = spnDifficolta.getSelectedItem().toString();
        parteCorpo = spnParteCorpo.getSelectedItem().toString();
        tipologia = spnTipologia.getSelectedItem().toString();
        modalita = spnModalita.getSelectedItem().toString();

        esercizi = dataBaseEsercizio.getEserciziFiltri(nome, gruppoMusc, difficolta, parteCorpo, tipologia, modalita);
        adapter.setEsercizi(esercizi);
        esRecView.setAdapter(adapter);

        if(adapter.getItemCount() == 0){
            txtEmptyEs.setVisibility(View.VISIBLE);
        }
    }
}