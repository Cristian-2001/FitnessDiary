package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ELENCO_ALL;
import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
import com.example.applicazione.dieta.AggiungiDietaActivity;
import com.example.applicazione.dieta.SelezionaCiboActivity;

import org.w3c.dom.Text;

public class SelezionaEsercizioActivity extends AppCompatActivity {
    //chiave per l'id dell'esercizio selezionato
    public static final String ES_ID_KEY = "esId";

    //variabile per controllare se è un nuovo allenamento o è già esistente
    // vale -2 se nuovo, l'id dell'allenamento se è già esistente, -1 default
    private int allenamentoId;

    private TextView txtNomeEsercizio, txtInsSerie, txtInsReps, txtInsRec;
    private EditText edtTxtSerie, edtTxtReps, edtTxtRec;
    private Button btnInsEs, btnAnnullaEs;

    //variabili per salvare i valori inseriti
    private int serie, reps, rec;

    private int esId;

    private ColorStateList oldColors;

    private Esercizio esercizio;

    private DataBaseEsercizio dataBaseEsercizio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_esercizio);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            esId = intent.getIntExtra(ES_ID_KEY, -1);
            if (esId != -1) {
                esercizio = dataBaseEsercizio.getEsercizioById(esId);
                txtNomeEsercizio.setText(esercizio.getNome());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelezionaEsercizioActivity.this);
                builder.setMessage("Errore nella selezione dell'esercizio");
                builder.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });

                final AlertDialog dialog = builder.create();
                dialog.show();
            }


            allenamentoId = intent.getIntExtra(ALLENAMENTO_ID_KEY, -1);
        }

        btnAnnullaEs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnInsEs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtTxtSerie.getText().toString().equals("")) {
                    txtInsSerie.setTextColor(getResources().getColor(R.color.red));
                }else {
                    txtInsSerie.setTextColor(oldColors);
                    serie = Integer.parseInt(edtTxtSerie.getText().toString());

                    if(edtTxtReps.getText().toString().equals("")) {
                        txtInsReps.setTextColor(getResources().getColor(R.color.red));
                    }else {
                        txtInsReps.setTextColor(oldColors);
                        reps = Integer.parseInt(edtTxtReps.getText().toString());

                        if(edtTxtRec.getText().toString().equals("")) {
                            txtInsRec.setTextColor(getResources().getColor(R.color.red));
                        }else {
                            txtInsRec.setTextColor(oldColors);
                            rec = Integer.parseInt(edtTxtRec.getText().toString());

                            AggiungiAllenamentoActivity.modificaAllenamento(esId, serie, reps, rec);

                            Toast.makeText(SelezionaEsercizioActivity.this, dataBaseEsercizio.getEsercizioById(esId).getNome()
                                            + " inserito", Toast.LENGTH_SHORT).show();

                            Intent intent1 = new Intent(SelezionaEsercizioActivity.this, AggiungiAllenamentoActivity.class);
                            intent1.putExtra(ELENCO_ALL, -1);
                            intent1.putExtra(ALLENAMENTO_ID_KEY, allenamentoId);
                            SelezionaEsercizioActivity.this.startActivity(intent1);
                        }
                    }
                }
            }
        });
    }

    private void initView(){
        txtNomeEsercizio = findViewById(R.id.txtNomeEsercizio);
        txtInsSerie = findViewById(R.id.txtInsSerie);
        txtInsReps = findViewById(R.id.txtInsReps);
        txtInsRec = findViewById(R.id.txtInsRec);

        edtTxtSerie = findViewById(R.id.edtTxtSerie);
        edtTxtReps=findViewById(R.id.edtTxtReps);
        edtTxtRec= findViewById(R.id.edtTxtRec);

        btnInsEs = findViewById(R.id.btnInsEs);
        btnAnnullaEs = findViewById(R.id.btnAnnullaEs);

        oldColors = txtInsSerie.getTextColors();

        dataBaseEsercizio = new DataBaseEsercizio(SelezionaEsercizioActivity.this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AggiungiDietaActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}