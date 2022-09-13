package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.applicazione.R;

public class SvolgiEsercizioActivity extends AppCompatActivity {
    public static final String NUMERO_SERIE = "numeroSerie";
    public static final String NUMERO_ES = "numeroEsercizio";
    private static final String TAG = "SvolgiEsercizioActivity";

    private TextView txtNumSerie, txtNomeEs, txtEsSucc, txtNumReps, txtRepsSucc;
    private Button btnInterrompi, btnAvviaRec;

    private DataBaseEsercizio dataBaseEsercizio;
    private DataBaseAllenamento dataBaseAllenamento;

    //id dell'allenamento avviato
    private int allenamentoid;

    //numero dell'esercizio
    private int numEs;

    //numero di esercizi totali
    private int esTot;

    //numero della serie
    private int numSerie;

    //allenamento che si sta svolgendo
    private Allenamento allenamento;

    //esercizio che si sta svolgendo
    private Esercizio esercizio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svolgi_esercizio);

        initView();

        //inizializzo i database
        dataBaseEsercizio = new DataBaseEsercizio(SvolgiEsercizioActivity.this);
        dataBaseAllenamento = new DataBaseAllenamento(SvolgiEsercizioActivity.this);

        Intent intent = getIntent();
        if (intent != null) {
            allenamentoid = intent.getIntExtra(ALLENAMENTO_ID_KEY, -1);
            numEs = intent.getIntExtra(NUMERO_ES, -1);
            numSerie = intent.getIntExtra(NUMERO_SERIE, -1);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(SvolgiEsercizioActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(SvolgiEsercizioActivity.this, ElencoAllenamentiActivity.class);
                    SvolgiEsercizioActivity.this.startActivity(intent1);
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        Log.d(TAG, "onCreate: ID: " + allenamentoid);
        //TODO sistemare i casi di errore
        if (allenamentoid != -1) {
            allenamento = dataBaseAllenamento.getAllenamentoById(allenamentoid);

            if (numEs != -1) {
                esercizio = dataBaseEsercizio.getEsercizioById(allenamento.getEserciziId().get(numEs));
                txtNomeEs.setText(esercizio.getNome());
                txtNumReps.setText(allenamento.getEserciziReps().get(numEs).toString() + " ripetizioni");

                if (numSerie != -1) {
                    if (numSerie <= allenamento.getEserciziSerie().get(numEs)) {
                        txtNumSerie.setText("Serie numero " + numSerie + " di " + allenamento.getEserciziSerie().get(numEs).toString());
                    }
                }

                esTot = allenamento.getNumElem();

                if (numEs != esTot - 1) {
                    txtEsSucc.setText(dataBaseEsercizio.getEsercizioById(allenamento.getEserciziId().get(numEs + 1)).getNome());
                    txtRepsSucc.setText(allenamento.getEserciziSerie().get(numEs + 1).toString() + " serie da " +
                            allenamento.getEserciziReps().get(numEs + 1).toString() + " ripetizioni ciascuna");
                }else{
                    txtEsSucc.setText("Allenamento finito");
                    txtRepsSucc.setVisibility(View.GONE);
                }
            }
        }

        btnInterrompi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SvolgiEsercizioActivity.this, VisualizzaAllenamentoActivity.class);
                intent1.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                SvolgiEsercizioActivity.this.startActivity(intent1);
            }
        });

        btnAvviaRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(SvolgiEsercizioActivity.this, TimerActivity.class);
                intent1.putExtra(ALLENAMENTO_ID_KEY, allenamentoid);
                intent1.putExtra(NUMERO_ES, numEs);
                intent1.putExtra(NUMERO_SERIE, numSerie);
                SvolgiEsercizioActivity.this.startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //non faccio nulla perché non voglio consentire di tornare indietro
    }

    private void initView() {
        txtNumSerie = findViewById(R.id.txtNumSerie);
        txtNomeEs = findViewById(R.id.txtNomeEs);
        txtEsSucc = findViewById(R.id.txtEsSucc);
        txtNumReps = findViewById(R.id.txtNumReps);
        txtRepsSucc = findViewById(R.id.txtRepsSucc);

        btnInterrompi = findViewById(R.id.btnInterrompi);
        btnAvviaRec = findViewById(R.id.btnAvviaRec);
    }
}