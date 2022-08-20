package com.example.applicazione.allenamento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class AggiungiAllenamentoActivity extends AppCompatActivity {
    private static final String TAG = "AggiungiAllenamActivity";    //TAG per i log

    private Spinner spnEsercizio1;

    private EditText edtTxtSerie1;
    private EditText edtTxtRep1;
    private EditText edtTxtTSer1;
    private EditText edtTxtRec1;

    private TextView txtWarnSerie1;
    private TextView txtWarnRep1;
    private TextView txtWarnTSer1;
    private TextView txtWarnRec1;

    private Button btnSalva;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_allenamento);

        initView();

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRegister();
            }
        });
    }

    private void initView() {
        /* inizializzo gli elementi dell'activity */
        spnEsercizio1 = findViewById(R.id.spnEsercizio1);

        edtTxtSerie1 = findViewById(R.id.edtTxtSerie1);
        edtTxtRep1 = findViewById(R.id.edtTxtRep1);
        edtTxtTSer1 = findViewById(R.id.edtTxtTSer1);
        edtTxtRec1 = findViewById(R.id.edtTxtRec1);

        btnSalva = findViewById(R.id.btnSalva);

        parent = findViewById(R.id.parent);

        txtWarnSerie1 = findViewById(R.id.txtWarnSerie1);
        txtWarnRep1 = findViewById(R.id.txtWarnRep1);
        txtWarnTSer1 = findViewById(R.id.txtWarnTSer1);
        txtWarnRec1 = findViewById(R.id.txtWarnRec1);
    }

    //metodo per salvare i dati
    public void initRegister() {
        //TODO: Salvare l'allenamento in un database

        if (validateData()) {
            showSnackBar();
        } else {
            Toast.makeText(AggiungiAllenamentoActivity.this, "Errore nell'inserimento dei dati", Toast.LENGTH_SHORT).show();
        }
    }

    //metodo per controllare la correttezza dei dati inseriti
    public boolean validateData() {
        Log.d(TAG, "validateData: Started");
        if (edtTxtSerie1.getText().toString().equals("") ||
                Integer.parseInt(edtTxtSerie1.getText().toString()) <= 0) {
            txtWarnSerie1.setVisibility(View.VISIBLE);
            return false;
        } else {
            txtWarnSerie1.setVisibility(View.GONE);
        }

        if (edtTxtRep1.getText().toString().equals("") ||
                Integer.parseInt(edtTxtRep1.getText().toString()) <= 0) {
            txtWarnRep1.setVisibility(View.VISIBLE);
            return false;
        } else {
            txtWarnRep1.setVisibility(View.GONE);
        }

        if (edtTxtTSer1.getText().toString().equals("") ||
                Integer.parseInt(edtTxtTSer1.getText().toString()) <= 0) {
            txtWarnTSer1.setVisibility(View.VISIBLE);
            return false;
        } else {
            txtWarnTSer1.setVisibility(View.GONE);
        }

        if (edtTxtRec1.getText().toString().equals("") ||
                Integer.parseInt(edtTxtRec1.getText().toString()) <= 0) {
            txtWarnRec1.setVisibility(View.VISIBLE);
            return false;
        } else {
            txtWarnRec1.setVisibility(View.GONE);
        }

        return true;
    }

    public void showSnackBar() {
        /* Per ora mostro l'allenamento inserito in una snackbar.
        Quando potremo, lo inseriremo direttamente nel database.
        Per vedere tutto il testo della snackbar e controllare che sia giusto, controlla nei log.
         */
        Log.d(TAG, "showSnackBar: Started");
        txtWarnSerie1.setVisibility(View.GONE);
        txtWarnRep1.setVisibility(View.GONE);
        txtWarnTSer1.setVisibility(View.GONE);
        txtWarnRec1.setVisibility(View.GONE);

        String esercizio = spnEsercizio1.getSelectedItem().toString();
        String serie = edtTxtSerie1.getText().toString();
        String rep = edtTxtRep1.getText().toString();
        String tser = edtTxtTSer1.getText().toString();
        String rec = edtTxtRec1.getText().toString();

        String snackText = "Esercizio: " + esercizio + "\n" +
                serie + " serie da " + rep + " ripetizioni ciascuna" + "\n" +
                "Tempo di esecuzione di una serie: " + tser + " secondi" + "\n" +
                "Tempo di recupero tra una serie e l'altra: " + rec + " secondi";

        Log.d(TAG, "showSnackBar: Snack Bar Text: " + snackText);

        Snackbar.make(parent, snackText, BaseTransientBottomBar.LENGTH_INDEFINITE).setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtTxtSerie1.setText("0");
                edtTxtRep1.setText("0");
                edtTxtTSer1.setText("0");
                edtTxtRec1.setText("0");
            }
        }).show();
    }
}