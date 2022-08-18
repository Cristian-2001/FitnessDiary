package com.example.applicazione.dieta;

import static com.example.applicazione.dieta.AggiungiDietaActivity.ELENCO_DIETE;
import static com.example.applicazione.dieta.VisualizzaDietaActivity.DIETA_ID_KEY;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;

public class SelezionaCiboActivity extends AppCompatActivity {
    //chiave per l'id del cibo selezionato
    public static final String CIBO_ID_KEY = "ciboId";

    //variabile per controllare se è una nuova dieta o è già esistente
    // vale -2 se nuova, l'id della dieta se è già esistente, -1 default
    private int dietaId;

    private TextView txtNomeCibo, txtInsMsg, txtValori;
    private EditText edtTxtQta;
    private Button btnAnnulla, btnInserisci, btnCalcola;
    private int ciboId;
    private Double qta;

    ColorStateList oldColors;

    private Cibo cibo;

    DataBaseCibo dataBaseCibo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleziona_cibo);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        Intent intent = getIntent();
        if (intent != null) {
            ciboId = intent.getIntExtra(CIBO_ID_KEY, -1);
            if (ciboId != -1) {
                cibo = dataBaseCibo.getCiboById(ciboId);
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

                final AlertDialog dialog = builder.create();
                dialog.show();
            }

            dietaId = intent.getIntExtra(DIETA_ID_KEY, -1);
        }

        btnCalcola.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (edtTxtQta.getText().toString().equals("")) {
                    Toast.makeText(SelezionaCiboActivity.this, "Inserire la quantità", Toast.LENGTH_SHORT).show();
                    txtInsMsg.setTextColor(getResources().getColor(R.color.red));
                }else{
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                    txtInsMsg.setTextColor(oldColors);

                    Double energia, lipidi, acidi_grassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale;
                    qta = Double.valueOf(edtTxtQta.getText().toString());
                    energia = cibo.getEnergia() * qta / 100;
                    lipidi = cibo.getLipidi() * qta / 100;
                    acidi_grassi = cibo.getAcidigrassi() * qta / 100;
                    colesterolo = cibo.getColesterolo() * qta / 100;
                    carboidrati = cibo.getCarboidrati() * qta / 100;
                    zuccheri = cibo.getZuccheri() * qta / 100;
                    fibre = cibo.getFibre() * qta / 100;
                    proteine = cibo.getProteine() * qta / 100;
                    sale = cibo.getSale() * qta / 100;

                    txtValori.setText("Calorie: " + energia + " kcal\n"
                            + "Lipidi: " + lipidi + " g\n"
                            + "Acidi grassi: " + acidi_grassi + " g\n"
                            + "Colesterolo: " + colesterolo + " g\n"
                            + "Carboidrati: " + carboidrati + " g\n"
                            + "Zuccheri: " + zuccheri + " g\n"
                            + "Fibre: " + fibre + " g\n"
                            + "Proteine: " + proteine + " g\n"
                            + "Sale: " + sale + " g\n");
                    txtValori.setVisibility(View.VISIBLE);
                }

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
                    txtInsMsg.setTextColor(getResources().getColor(R.color.red));
                } else {
                    AggiungiDietaActivity.modificaDieta(ciboId, qta);

                    Toast.makeText(SelezionaCiboActivity.this, dataBaseCibo.getCiboById(ciboId).getNome() + " inserito",
                            Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(SelezionaCiboActivity.this, AggiungiDietaActivity.class);
                    intent1.putExtra(ELENCO_DIETE, -1);
                    intent1.putExtra(DIETA_ID_KEY, dietaId);
                    SelezionaCiboActivity.this.startActivity(intent1);
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

        oldColors = txtInsMsg.getTextColors();

        dataBaseCibo = new DataBaseCibo(SelezionaCiboActivity.this);
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