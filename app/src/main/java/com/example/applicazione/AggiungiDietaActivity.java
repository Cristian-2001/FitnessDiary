package com.example.applicazione;

import static com.example.applicazione.VisualizzaDietaActivity.DIETA_ID_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AggiungiDietaActivity extends AppCompatActivity {

    private static final String TAG = "AggiungiDietaActivity";
    public static final String DIETA_NOME = "dietaNome";
    public static final String ELENCO_DIETE = "elencoDiete";

    //id della dieta da modificare
    private static int dietaId;

    private Spinner spnCategoria;
    private Button btnCerca;
    private RecyclerView cibiRecView;
    private CibiRecViewAdapter adapter;
    private List<Cibo> cibi = new ArrayList<>();
    private EditText edtTxtCercaNome;
    private TextView txtEmpty;
    private TextView txtNumCorr;
    private Button btnSalvaDieta;

    public static Dieta dieta = null;
    private static List<Integer> cibiDieta = new ArrayList<>();
    private static List<Double> qtaDieta = new ArrayList<>();
    private static int num = 0;
    private static String nomeDieta = null;

    DataBaseCibo dataBaseCibo;
    DataBaseDieta dataBaseDieta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_dieta);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        dataBaseDieta = new DataBaseDieta(AggiungiDietaActivity.this);
        dataBaseCibo = new DataBaseCibo(AggiungiDietaActivity.this);
        cibi = dataBaseCibo.getAllCibi();
        adapter.setCibi(cibi);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.getIntExtra(ELENCO_DIETE, -1) == 1) {
                Log.d(TAG, "onCreate: ECCOMI");

                nomeDieta = intent.getStringExtra(DIETA_NOME);
                cibiDieta.clear();
                qtaDieta.clear();
                num = 0;
            }

            //prendo l'eventuale id della dieta e setto i dati in base alla dieta
            // se è uguale a -2 significa che sono arrivato da SelezionaCiboActivity quindi non devo modificarlo
            if (intent.getIntExtra(DIETA_ID_KEY, -1) > -1) {
                dietaId = intent.getIntExtra(DIETA_ID_KEY, -1);
                nomeDieta = dataBaseDieta.getDietaById(dietaId).getNome();
                cibiDieta = dataBaseDieta.getDietaById(dietaId).getCibiId();
                qtaDieta = dataBaseDieta.getDietaById(dietaId).getCibiQta();
                num = dataBaseDieta.getDietaById(dietaId).getNumElem();
            } else if (intent.getIntExtra(DIETA_ID_KEY, -1) == -2) {
                dietaId = intent.getIntExtra(DIETA_ID_KEY, -1);
            }
        }
        Log.d(TAG, "onCreate: NOME: " + nomeDieta);
        Log.d(TAG, "onCreate: NUM: " + num);
        Log.d(TAG, "onCreate: ID: " + dietaId);

        if (num == 1) {
            txtNumCorr.setText(num + " elemento");
        } else {
            txtNumCorr.setText(num + " elementi");
        }

        btnCerca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm.isAcceptingText()) {
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
                txtEmpty.setVisibility(View.GONE);

                String cat = spnCategoria.getSelectedItem().toString();
                String nome = edtTxtCercaNome.getText().toString();

                if (cat.equals("Tutte") && nome.equals("")) {
                    cibi = dataBaseCibo.getAllCibi();
                } else {
                    Log.d(TAG, "onClick: ECCOMI");
                    cibi = dataBaseCibo.getCibiByCatNome(cat, nome);
                }

                adapter.setCibi(cibi);
                cibiRecView.setAdapter(adapter);

                if (adapter.getItemCount() == 0) {
                    txtEmpty.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSalvaDieta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (num == 0) {
                    Toast.makeText(AggiungiDietaActivity.this, "Inserire almeno un elemento", Toast.LENGTH_SHORT).show();
                } else {

                    dieta = new Dieta(nomeDieta, cibiDieta, qtaDieta, num);

                    Log.d(TAG, "onClick: ID: " + dietaId);
                    if (dietaId > -1) {
                        Log.d(TAG, "onClick: ECCOMI");
                        dataBaseDieta.modificaDieta(dietaId, dieta);
                    } else {
                        Log.d(TAG, "onClick: SONO QUI");
                        dataBaseDieta.addOne(dieta);
                    }

                    Intent intent1 = new Intent(AggiungiDietaActivity.this, ElencoDieteActivity.class);
                    intent1.putExtra(DIETA_ID_KEY, dieta.getId());
                    AggiungiDietaActivity.this.startActivity(intent1);
                }
            }
        });
    }

    //creo e inizializzo tutti gli elementi dell'Activity
    private void initView() {
        adapter = new CibiRecViewAdapter(this);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnCerca = findViewById(R.id.btnCerca);
        cibiRecView = findViewById(R.id.cibiRecView);
        edtTxtCercaNome = findViewById(R.id.edtTxtCercaNome);
        txtEmpty = findViewById(R.id.txtEmpty);
        txtNumCorr = findViewById(R.id.txtNumCorr);
        btnSalvaDieta = findViewById(R.id.btnSalvaDieta);


        cibiRecView.setAdapter(adapter);
        cibiRecView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public static void modificaDieta(int ciboId, Double ciboQta) {
        Log.d(TAG, "modificaDieta: Called");
        cibiDieta.add(ciboId);
        qtaDieta.add(ciboQta);
        num++;
        Log.d(TAG, "modificaDieta: NUM: " + num);
    }

    public static List<Integer> getDietaCibiId() {
        return dieta.getCibiId();
    }

    public static List<Double> getDietaCibiQta() {
        return dieta.getCibiQta();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(AggiungiDietaActivity.this);
                builder.setMessage("La dieta corrente non verrà salvata. Vuoi tornare indietro?");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AggiungiDietaActivity.this.finish();
                        Intent intent = new Intent(AggiungiDietaActivity.this, ElencoDieteActivity.class);
                        AggiungiDietaActivity.this.startActivity(intent);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(AggiungiDietaActivity.this);
        builder.setMessage("La dieta corrente non verrà salvata. Vuoi tornare indietro?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AggiungiDietaActivity.this.finish();
                Intent intent = new Intent(AggiungiDietaActivity.this, ElencoDieteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Annulla", null);

        final AlertDialog dialog = builder.create();
        dialog.show();
    }
}