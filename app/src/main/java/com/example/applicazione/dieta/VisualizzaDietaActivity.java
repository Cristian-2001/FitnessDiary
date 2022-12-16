package com.example.applicazione.dieta;


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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
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
    private CibiDietaRecViewAdapter adapter;

    //boolean per controllare se ci sono state delle modifiche non salvate
    private static boolean modificato = false;

    //boolean per controllare se ho eliminato l'ultimo elemento di una dieta
    private static boolean last_item = false;

    //id della dieta ricevuto dall'activity ElencoDiete
    private int dietaId;

    //numero di elementi della dieta
    private int numElem;

    //List degli id dei cibi
    private List<Integer> cibiId;

    //List delle quantità dei cibi
    private List<Double> cibiQta;

    //List dei cibi
    private List<Cibo> cibi = new ArrayList<>();

    //nome della dieta
    String nomeDieta;

    private DataBaseDieta dataBaseDieta;
    private DataBaseCibo dataBaseCibo;

    public static void setModificato() {
        VisualizzaDietaActivity.modificato = true;
    }

    public static void setLast_item() {
        VisualizzaDietaActivity.last_item = true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_dieta);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

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
            nomeDieta = dataBaseDieta.getDietaById(dietaId).getNome();
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

        if (numElem == 1) {
            txtElemSel.setText(numElem + " elemento");
        } else {
            txtElemSel.setText(numElem + " elementi");
        }

        btnValoriTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!last_item) {
                    String valori = calcolaValori(cibi, cibiQta);
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaDietaActivity.this);
                    builder.setMessage(valori);
                    builder.setNegativeButton("Ok", null);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
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
        inflater.inflate(R.menu.elenco_diete_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modifica_nome:
                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setMessage("Inserisci il nuovo nome: ");

                final EditText input = new EditText(this);
                input.setText(nomeDieta);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder2.setView(input);

                builder2.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder2.setNegativeButton("Annulla", null);

                final AlertDialog dialog2 = builder2.create();
                dialog2.setView(input, 60, 0, 60, 0);
                dialog2.show();

                dialog2.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(VisualizzaDietaActivity.this, "Inserire il nome", Toast.LENGTH_SHORT).show();
                        } else {
                            nomeDieta = input.getText().toString();
                            dataBaseDieta.modificaNome(dietaId, nomeDieta);
                            txtDietaSel.setText(nomeDieta);
                            dialog2.dismiss();
                        }
                    }
                });
                return true;

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
                            Intent intent = new Intent(VisualizzaDietaActivity.this, ElencoDieteActivity.class);
                            VisualizzaDietaActivity.this.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Annulla", null);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //altrimenti salvo la nuova dieta
                    Dieta dieta = new Dieta(dietaId, nomeDieta,
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
                goBack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initView() {
        txtDietaSel = findViewById(R.id.txtAllSel);
        txtElemSel = findViewById(R.id.txtElemSelA);
        txtEmptyDieta = findViewById(R.id.txtEmptyAllenamento);
        btnValoriTot = findViewById(R.id.btnValoriTot);
        cibiDietaRecView = findViewById(R.id.esAllRecView);
        fltABAddCibo = findViewById(R.id.fltABAddCibo);

        adapter = new CibiDietaRecViewAdapter(this);

        cibiDietaRecView.setAdapter(adapter);
        cibiDietaRecView.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint("DefaultLocale")
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

        String ret = String.format("Valori nutrizionali complessivi:\n\n"
                + "Calorie: %.2f kcal\n"
                + "Lipidi: %.2f g\n"
                + "Acidi grassi: %.2f g\n"
                + "Colesterolo: %.2f g\n"
                + "Carboidrati: %.2f g\n"
                + "Zuccheri: %.2f g\n"
                + "Fibre: %.2f g\n"
                + "Proteine: %.2f g\n"
                + "Sale: %.2f g\n",
                enerTot, lipTot, acidiTot, colTot, carboTot, zuccTot, fibreTot, protTot, saleTot);
        return ret;
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    /**
     * funzione per tornare indietro
     * faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
     * controllo anche che non ci siano modifiche non salvate, altrimenti lo segnalo
     */
    private void goBack() {
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