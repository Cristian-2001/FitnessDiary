package com.example.applicazione;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VisualizzaDietaActivity extends AppCompatActivity {
    private static final String TAG = "VisualizzaDietaActivity";
    public static final String DIETA_ID_KEY = "dietaId";

    private TextView txtDietaSel, txtElemSel, txtEmptyDieta;
    private Button btnValoriTot;
    private FloatingActionButton fltABAddCibo;

    private RecyclerView cibiDietaRecView;
    private CibiDietaRecViewAdapter adapter = new CibiDietaRecViewAdapter(VisualizzaDietaActivity.this);

    //boolean per controllare se ci sono state delle modifiche non salvate
    private static boolean modificato = false;

    //boolean per controllare se ho eliminato l'ultimo elemento di una dieta
    private static boolean last_item = false;

    //id della dieta ricevuto dall'activity ElencoDiete
    private int dietaId;

    //numero di elementi della dieta
    private int numElem;

    //ArrayList degli id dei cibi
    private List<Integer> cibiId;

    //ArrayList delle quantità dei cibi
    private List<Double> cibiQta;

    //ArrayList dei cibi
    private List<Cibo> cibi = new ArrayList<>();

    private DataBaseDieta dataBaseDieta;
    private DataBaseCibo dataBaseCibo;

    public static void setModificato() {
        VisualizzaDietaActivity.modificato = true;
    }

    public static void setLast_item() {
        VisualizzaDietaActivity.last_item = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_dieta);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        actionBar.setDisplayHomeAsUpEnabled(true);

        dataBaseDieta = new DataBaseDieta(VisualizzaDietaActivity.this);
        dataBaseCibo = new DataBaseCibo(VisualizzaDietaActivity.this);

        initView();
        modificato = false;
        last_item = false;

        Intent intent = getIntent();
        if (intent != null) {
            dietaId = intent.getIntExtra(DIETA_ID_KEY, -1);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                    VisualizzaDietaActivity.this.startActivity(intent1);
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (dietaId != -1) {
            cibiId = dataBaseDieta.getDietaById(dietaId).getCibiId();
            cibiQta = dataBaseDieta.getDietaById(dietaId).getCibiQta();
            numElem = dataBaseDieta.getDietaById(dietaId).getNumElem();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                    VisualizzaDietaActivity.this.startActivity(intent1);
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        for (int id : cibiId) {
            Log.d(TAG, "onCreate: CIAO " + dataBaseCibo.getCiboById(id).toString());
            cibi.add(dataBaseCibo.getCiboById(id));
        }

        Log.d(TAG, "onCreate: CIBI: " + cibi.toString());

        //setto l'adapter
        adapter.setCibiId(cibiId);
        adapter.setQuantita(cibiQta);
        adapter.setNumElem(numElem);

        if (adapter.getItemCount() == 0) {
            txtEmptyDieta.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "onCreate: NOME: " + dataBaseDieta.getDietaById(dietaId).getNome());
        Log.d(TAG, "onCreate: NUM: " + dataBaseDieta.getDietaById(dietaId).getNumElem());
        txtDietaSel.setText(dataBaseDieta.getDietaById(dietaId).getNome());
        txtElemSel.setText(numElem + " elementi");

        btnValoriTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valori = calcolaValori(cibi, cibiQta);
                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
                builder.setMessage(valori);
                builder.setNegativeButton("Ok", null);
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        fltABAddCibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(VisualizzaDietaActivity.this, AggiungiDietaActivity.class);
                intent2.putExtra(DIETA_ID_KEY, dietaId);
                VisualizzaDietaActivity.this.startActivity(intent2);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.visualizza_dieta_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salva_menu:
                modificato = false;

                Log.d(TAG, "onOptionsItemSelected: LAST: " + last_item);

                //se ho eliminato l'ultimo elemento
                if (last_item) {
                    //avviso che la dieta è rimasta vuota e verrà eliminata
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
                    builder.setMessage("La dieta è vuota e verrà eliminata. Continuare?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataBaseDieta.eliminaDieta(dietaId);
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    });
                    builder.setNegativeButton("Annulla", null);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //altrimenti salvo la nuova dieta
                    Dieta dieta = new Dieta(dietaId, dataBaseDieta.getDietaById(dietaId).getNome(),
                            cibiId, cibiQta, adapter.getItemCount());
                    dataBaseDieta.modificaDieta(dietaId, dieta);
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
                return true;

            case R.id.elimina_menu:
                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
                builder.setMessage("Eliminare la dieta " + dataBaseDieta.getDietaById(dietaId).getNome() + "?");
                builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        VisualizzaDietaActivity.this.finish();
                        Intent intent = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                        VisualizzaDietaActivity.this.startActivity(intent);
                        dataBaseDieta.eliminaDieta(dietaId);
                    }
                });
                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case android.R.id.home:
                //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
                //controllo anche che non ci siano modifiche non salvate, altrimenti lo segnalo
                if (modificato) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(VisualizzaDietaActivity.this);
                    builder1.setMessage("Le modifiche non verranno salvate. Vuoi tornare indietro?");
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            VisualizzaDietaActivity.this.finish();
                            Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            VisualizzaDietaActivity.this.startActivity(intent1);
                        }
                    });
                    builder1.setNegativeButton("Annulla", null);

                    final AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                } else {
                    VisualizzaDietaActivity.this.finish();
                    Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    VisualizzaDietaActivity.this.startActivity(intent1);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        txtDietaSel = findViewById(R.id.txtDietaSel);
        txtElemSel = findViewById(R.id.txtElemSel);
        txtEmptyDieta = findViewById(R.id.txtEmptyDieta);
        btnValoriTot = findViewById(R.id.btnValoriTot);
        cibiDietaRecView = findViewById(R.id.cibiDietaRecView);
        fltABAddCibo = findViewById(R.id.fltABAddCibo);

        adapter = new CibiDietaRecViewAdapter(this);

        cibiDietaRecView.setAdapter(adapter);
        cibiDietaRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String calcolaValori(List<Cibo> cibi, List<Double> quantita) {
        Log.d(TAG, "calcolaValori: Called");

        Double qta;
        double enerTot = 0.;
        double lipTot = 0.;
        double acidiTot = 0.;
        double colTot = 0.;
        double carboTot = 0.;
        double zuccTot = 0.;
        double fibreTot = 0.;
        double protTot = 0.;
        double saleTot = 0.;

        for (int i = 0; i < cibi.size(); i++) {
            qta = quantita.get(i);
            enerTot += cibi.get(i).getEnergia() * qta / 100;
            lipTot += cibi.get(i).getLipidi() * qta / 100;
            acidiTot += cibi.get(i).getAcidigrassi() * qta / 100;
            colTot += cibi.get(i).getColesterolo() * qta / 100;
            carboTot += cibi.get(i).getCarboidrati() * qta / 100;
            zuccTot += cibi.get(i).getZuccheri() * qta / 100;
            fibreTot += cibi.get(i).getFibre() * qta / 100;
            protTot += cibi.get(i).getProteine() * qta / 100;
            saleTot += cibi.get(i).getSale() * qta / 100;
        }

        String ret = "Valori nutrizionali complessivi:\n\n"
                + "Calorie: " + enerTot + " kcal\n"
                + "Lipidi: " + lipTot + " g\n"
                + "Acidi grassi: " + acidiTot + " g\n"
                + "Colesterolo: " + colTot + " g\n"
                + "Carboidrati: " + carboTot + " g\n"
                + "Zuccheri: " + zuccTot + " g\n"
                + "Fibre: " + fibreTot + " g\n"
                + "Proteine: " + protTot + " g\n"
                + "Sale: " + saleTot + " g\n";
        return ret;
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    //controllo anche che non ci siano modifiche non salvate, altrimenti lo segnalo
    @Override
    public void onBackPressed() {
        if (modificato) {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
            builder.setMessage("Le modifiche non verranno salvate. Vuoi tornare indietro?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    VisualizzaDietaActivity.this.finish();
                    Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    VisualizzaDietaActivity.this.startActivity(intent1);
                }
            });
            builder.setNegativeButton("Annulla", null);

            final AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            VisualizzaDietaActivity.this.finish();
            Intent intent1 = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            VisualizzaDietaActivity.this.startActivity(intent1);
        }
    }
}