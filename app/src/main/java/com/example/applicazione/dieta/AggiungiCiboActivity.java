package com.example.applicazione.dieta;

import static com.example.applicazione.dieta.AggiungiDietaActivity.DIETA_NOME;
import static com.example.applicazione.dieta.AggiungiDietaActivity.ELENCO_DIETE;
import static com.example.applicazione.dieta.VisualizzaDietaActivity.DIETA_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;

public class AggiungiCiboActivity extends AppCompatActivity {
    private static final String TAG = "AggiungiCiboActivity";

    private EditText edtTxtNome, edtTxtEnergia, edtTxtLipidi, edtTxtAcidiGrassi, edtTxtColesterolo,
            edtTxtCarboidrati, edtTxtZuccheri, edtTxtFibre, edtTxtProteine, edtTxtSale;
    private Spinner spnCat;
    private Button btnUndo, btnSave;
    private TextView txtNome, txtEnergia, txtLipidi, txtAcidiGrassi, txtColesterolo,
            txtCarboidrati, txtZuccheri, txtFibre, txtProteine, txtSale;

    private ColorStateList oldColors;

    private String temp, nome, cat;
    private double energia, lipidi, acidigrassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale;

    //variabili ricevute da AggiungiDietaActivity
    private int dietaId, elencoDiete;
    private String nomeDieta;

    private DataBaseCibo dataBaseCibo;

    //dato per controllare se sono stati inseriti correttamente tutti i dati
    private boolean allData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_cibo);

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

        //btnUndo chiude l'activity senza salvare nulla
        btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //btnSave controlla che siano stati inseriti tutti i campi, che non ci sia già un cibo con lo stesso nome
        // e salva il nuovo cibo
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allData = true;

                if (edtTxtNome.getText().toString().equals("")) {
                    txtNome.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtNome.setTextColor(oldColors);
                    temp = edtTxtNome.getText().toString();
                    nome = temp.substring(0, 1).toUpperCase() + temp.substring(1);

                    if (dataBaseCibo.getNomi().contains(nome)) {
                        Toast.makeText(AggiungiCiboActivity.this, "Il cibo " + nome + " esiste già", Toast.LENGTH_SHORT).show();
                        allData = false;
                    }
                }


                if (edtTxtEnergia.getText().toString().equals("")) {
                    txtEnergia.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtEnergia.setTextColor(oldColors);
                    energia = Double.parseDouble(edtTxtEnergia.getText().toString());
                    if(energia > 1000){
                        txtEnergia.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "L'energia non può superare le 1000 kcal", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtLipidi.getText().toString().equals("")) {
                    txtLipidi.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtLipidi.setTextColor(oldColors);
                    lipidi = Double.parseDouble(edtTxtLipidi.getText().toString());
                    if(lipidi > 100){
                        txtLipidi.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore di lipidi non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtAcidiGrassi.getText().toString().equals("")) {
                    txtAcidiGrassi.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtAcidiGrassi.setTextColor(oldColors);
                    acidigrassi = Double.parseDouble(edtTxtAcidiGrassi.getText().toString());
                    if(acidigrassi > 100){
                        txtAcidiGrassi.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore di acidi grassi non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtColesterolo.getText().toString().equals("")) {
                    txtColesterolo.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtColesterolo.setTextColor(oldColors);
                    colesterolo = Double.parseDouble(edtTxtColesterolo.getText().toString());
                    if(colesterolo > 100){
                        txtColesterolo.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore di colesterolo non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtCarboidrati.getText().toString().equals("")) {
                    txtCarboidrati.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtCarboidrati.setTextColor(oldColors);
                    carboidrati = Double.parseDouble(edtTxtCarboidrati.getText().toString());
                    if(carboidrati > 100){
                        txtCarboidrati.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore dei carboidrati non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtZuccheri.getText().toString().equals("")) {
                    txtZuccheri.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtZuccheri.setTextColor(oldColors);
                    zuccheri = Double.parseDouble(edtTxtZuccheri.getText().toString());
                    if(zuccheri > 100){
                        txtZuccheri.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore degli zuccheri non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtFibre.getText().toString().equals("")) {
                    txtFibre.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtFibre.setTextColor(oldColors);
                    fibre = Double.parseDouble(edtTxtFibre.getText().toString());
                    if(fibre > 100){
                        txtFibre.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore delle fibre non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtProteine.getText().toString().equals("")) {
                    txtProteine.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtProteine.setTextColor(oldColors);
                    proteine = Double.parseDouble(edtTxtProteine.getText().toString());
                    if(proteine > 100){
                        txtProteine.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore di proteine non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }


                if (edtTxtSale.getText().toString().equals("")) {
                    txtSale.setTextColor(getResources().getColor(R.color.red));
                    allData = false;
                } else {
                    txtSale.setTextColor(oldColors);
                    sale = Double.parseDouble(edtTxtSale.getText().toString());
                    if(sale > 100){
                        txtSale.setTextColor(getResources().getColor(R.color.red));
                        allData = false;
                        Toast.makeText(AggiungiCiboActivity.this, "Il valore di sale non può superare i 100 g", Toast.LENGTH_SHORT).show();
                    }
                }

                cat = spnCat.getSelectedItem().toString();

                if(allData){
                    boolean inserito = dataBaseCibo.addOne(new Cibo(nome, cat, energia, lipidi, acidigrassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale));
                    Log.d(TAG, "onClick: INSERITO: " + inserito);

                    //chiamo AggiungiDietaAcitivity passandogli i parametri che aveva già in precedenza
                    Intent intent1 = new Intent(AggiungiCiboActivity.this, AggiungiDietaActivity.class);
                    intent1.putExtra(DIETA_ID_KEY, -3);
                    intent1.putExtra(ELENCO_DIETE, elencoDiete);
                    intent1.putExtra(DIETA_NOME, nomeDieta);
                    AggiungiCiboActivity.this.startActivity(intent1);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Il cibo corrente non verrà salvato. Vuoi tornare indietro?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AggiungiCiboActivity.this.finish();
            }
        });
        builder.setNegativeButton("Annulla", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(AggiungiCiboActivity.this);
                builder.setMessage("Il cibo corrente non verrà salvato. Vuoi tornare indietro?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AggiungiCiboActivity.this.finish();
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

    /**
     * inizializzo i componenti dell'activity
     */
    private void initView() {
        edtTxtNome = findViewById(R.id.edtTxtNome);
        edtTxtEnergia = findViewById(R.id.edtTxtEnergia);
        edtTxtLipidi = findViewById(R.id.edtTxtLipidi);
        edtTxtAcidiGrassi = findViewById(R.id.edtTxtAcidiGrassi);
        edtTxtColesterolo = findViewById(R.id.edtTxtColesterolo);
        edtTxtCarboidrati = findViewById(R.id.edtTxtCarboidrati);
        edtTxtZuccheri = findViewById(R.id.edtTxtZuccheri);
        edtTxtFibre = findViewById(R.id.edtTxtFibre);
        edtTxtProteine = findViewById(R.id.edtTxtProteine);
        edtTxtSale = findViewById(R.id.edtTxtSale);

        spnCat = findViewById(R.id.spnCat);

        btnUndo = findViewById(R.id.btnUndo);
        btnSave = findViewById(R.id.btnSave);

        txtNome = findViewById(R.id.txtNome);
        txtEnergia = findViewById(R.id.txtEnergia);
        txtLipidi = findViewById(R.id.txtLipidi);
        txtAcidiGrassi = findViewById(R.id.txtAcidiGrassi);
        txtColesterolo = findViewById(R.id.txtColesterolo);
        txtCarboidrati = findViewById(R.id.txtCarboidrati);
        txtZuccheri = findViewById(R.id.txtZuccheri);
        txtFibre = findViewById(R.id.txtFibre);
        txtProteine = findViewById(R.id.txtProteine);
        txtSale = findViewById(R.id.txtSale);

        oldColors = txtNome.getTextColors();
    }
}