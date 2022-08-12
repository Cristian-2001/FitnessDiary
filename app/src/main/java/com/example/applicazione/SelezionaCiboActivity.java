package com.example.applicazione;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelezionaCiboActivity extends AppCompatActivity {
    public static final String CIBO_ID_KEY = "ciboId";

    private TextView txtNomeCibo, txtInsMsg, txtValori;
    private EditText edtTxtQta;
    private Button btnAnnulla, btnInserisci, btnCalcola;

    private Cibo cibo;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_cibo);

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            int ciboId = intent.getIntExtra(CIBO_ID_KEY, -1);
            if (ciboId != -1) {
                cibo = dataBaseHelper.getCiboById(ciboId);
                txtNomeCibo.setText(cibo.getNome());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(SelezionaCiboActivity.this);
                builder.setMessage("Errore nella selezione del cibo");
                builder.setPositiveButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            }
        }

        btnCalcola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                Double energia, lipidi, acidi_grassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale;
                Double qta = Double.valueOf(edtTxtQta.getText().toString());
                energia = cibo.getEnergia() * qta / 100;
                lipidi = cibo.getLipidi() * qta / 100;
                acidi_grassi = cibo.getAcidigrassi() * qta / 100;
                colesterolo = cibo.getColesterolo() * qta / 100;
                carboidrati = cibo.getCarboidrati() * qta / 100;
                zuccheri = cibo.getZuccheri() * qta / 100;
                fibre = cibo.getFibre() * qta / 100;
                proteine = cibo.getProteine() * qta / 100;
                sale = cibo.getSale() * qta / 100;

                txtValori.setText("Energia: " + energia.toString() + " kcal\n"
                        + "Lipidi: " + lipidi.toString() + " g\n"
                        + "Acidi grassi: " + acidi_grassi.toString() + " g\n"
                        + "Colesterolo: " + colesterolo.toString() + " g\n"
                        + "Carboidrati: " + carboidrati.toString() + " g\n"
                        + "Zuccheri: " + zuccheri.toString() + " g\n"
                        + "Fibre: " + fibre.toString() + " g\n"
                        + "Proteine: " + proteine.toString() + " g\n"
                        + "Sale: " + sale.toString() + " g\n");
                txtValori.setVisibility(View.VISIBLE);
            }
        });

        btnAnnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnInserisci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtTxtQta.getText().toString().equals("")) {
                    Toast.makeText(SelezionaCiboActivity.this, "Inserire la quantità", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SelezionaCiboActivity.this, "Inserisci cliccato", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //inizializzo gli elementi dell'Activity
    public void initView() {
        txtNomeCibo = findViewById(R.id.txtNomeCibo);
        txtInsMsg = findViewById(R.id.txtInsMsg);
        txtValori = findViewById(R.id.txtValori);

        edtTxtQta = findViewById(R.id.edtTxtQta);

        btnAnnulla = findViewById(R.id.btnAnnulla);
        btnInserisci = findViewById(R.id.btnInserisci);
        btnCalcola = findViewById(R.id.btnCalcola);

        dataBaseHelper = new DataBaseHelper(SelezionaCiboActivity.this);
    }
}