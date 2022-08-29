package com.example.applicazione.allenamento;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicazione.R;
import com.example.applicazione.dieta.AggiungiDietaActivity;
import com.example.applicazione.dieta.Dieta;
import com.example.applicazione.dieta.ElencoDieteActivity;
import com.example.applicazione.dieta.VisualizzaDietaActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VisualizzaAllenamentoActivity extends AppCompatActivity {
    public static final String ALLENAMENTO_ID_KEY = "allenamentoId";

    private TextView txtAllSel, txtElemSelA, txtEmptyAllenamento;
    private RecyclerView esAllRecView;
    private EsAllenamentoRecViewAdapter adapter;
    private FloatingActionButton fltABAddAllenamento;

    //id dell'allenamento ricevuto dall'activity ElencoAllenamenti
    private int allenamentoId;

    //numero di elementi dell'allenamento
    private int numElem;

    //List degli id degli esercizi
    List<Integer> eserciziId;

    //List delle serie
    List<Integer> eserciziSerie;

    //List delle ripetizioni
    List<Integer> eserciziReps;

    //List dei tempi di recupero
    List<Integer> eserciziTRec;

    //List degli esercizi
    List<Esercizio> esercizi = new ArrayList<>();

    //nome dell'allenamento
    String nomeAllenamento;

    //boolean per controllare se ci sono state delle modifiche non salvate
    private static boolean modificato = false;

    //boolean per controllare se ho eliminato l'ultimo elemento di un allenamento
    private static boolean last_item = false;

    private DataBaseAllenamento dataBaseAllenamento;
    private DataBaseEsercizio dataBaseEsercizio;

    public static void setModificato() {
        VisualizzaAllenamentoActivity.modificato = true;
    }

    public static void setLast_item() {
        VisualizzaAllenamentoActivity.last_item = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizza_allenamento);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dataBaseAllenamento = new DataBaseAllenamento(VisualizzaAllenamentoActivity.this);
        dataBaseEsercizio = new DataBaseEsercizio(VisualizzaAllenamentoActivity.this);

        initView();
        modificato = false;
        last_item = false;

        Intent intent = getIntent();
        if (intent != null) {
            allenamentoId = intent.getIntExtra(ALLENAMENTO_ID_KEY, -1);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                    VisualizzaAllenamentoActivity.this.startActivity(intent1);
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        if (allenamentoId != -1) {
            numElem = dataBaseAllenamento.getAllenamentoById(allenamentoId).getNumElem();
            eserciziId = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziId();
            eserciziSerie = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziSerie();
            eserciziReps = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziReps();
            eserciziTRec = dataBaseAllenamento.getAllenamentoById(allenamentoId).getEserciziTrec();
            nomeAllenamento = dataBaseAllenamento.getAllenamentoById(allenamentoId).getNome();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
            builder.setMessage("Si è verificato un errore");
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                    VisualizzaAllenamentoActivity.this.startActivity(intent1);
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();
        }

        for(int id : eserciziId){
            esercizi.add(dataBaseEsercizio.getEsercizioById(id));
        }

        //setto l'adapter
        adapter.setEserciziId(eserciziId);
        adapter.setEsercizi(esercizi);
        adapter.setSerie(eserciziSerie);
        adapter.setReps(eserciziReps);
        adapter.settRec(eserciziTRec);
        adapter.setNumElem(numElem);

        txtAllSel.setText(nomeAllenamento);

        if(numElem == 1){
            txtElemSelA.setText(numElem + " elemento");
        }else{
            txtElemSelA.setText(numElem + " elementi");
        }


        fltABAddAllenamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(VisualizzaAllenamentoActivity.this, AggiungiAllenamentoActivity.class);
                intent2.putExtra(ALLENAMENTO_ID_KEY, allenamentoId);
                VisualizzaAllenamentoActivity.this.startActivity(intent2);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.visualizza_elenco_menu, menu);
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
                dialog2.show();

                dialog2.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(VisualizzaAllenamentoActivity.this, "Inserire il nome", Toast.LENGTH_SHORT).show();
                        } else {
                            nomeAllenamento = input.getText().toString();
                            dataBaseAllenamento.modificaNome(allenamentoId, nomeAllenamento);
                            txtAllSel.setText(nomeAllenamento);
                            dialog2.dismiss();
                        }
                    }
                });
                return true;

            case R.id.salva_menu:
                //TODO aggiungi le modifiche all'allenamento poi metti a posto il salvataggio
                modificato = false;

                //se ho eliminato l'ultimo elemento
                if (last_item) {
                    //avviso che l'allenamento è rimasto vuoto e verrà eliminato
                    AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
                    builder.setMessage("L'allenamento è vuoto e verrà eliminato. Continuare?");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dataBaseAllenamento.eliminaAllenamento(allenamentoId);
                            Intent intent = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                            VisualizzaAllenamentoActivity.this.startActivity(intent);
                        }
                    });
                    builder.setNegativeButton("Annulla", null);

                    final AlertDialog dialog = builder.create();
                    dialog.show();
                } else {
                    //altrimenti salvo il nuovo allenamento
                    Allenamento allenamento = new Allenamento(allenamentoId, nomeAllenamento, eserciziId,
                            eserciziSerie, eserciziReps, eserciziTRec, adapter.getItemCount());
                    dataBaseAllenamento.modificaAllenamento(allenamentoId, allenamento);
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
                return true;

            case R.id.elimina_menu:
                AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
                builder.setMessage("Eliminare l'allenamento " + dataBaseAllenamento.getAllenamentoById(allenamentoId).getNome() + "?");
                builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        VisualizzaAllenamentoActivity.this.finish();
                        Intent intent = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                        VisualizzaAllenamentoActivity.this.startActivity(intent);
                        dataBaseAllenamento.eliminaAllenamento(allenamentoId);
                    }
                });
                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();
                return true;

            case android.R.id.home:
                //TODO Aggiungi le modifiche all'allenamento poi metti a posto pure qui
                //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
                //controllo anche che non ci siano modifiche non salvate, altrimenti lo segnalo
                if (modificato) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
                    builder1.setMessage("Le modifiche non verranno salvate. Vuoi tornare indietro?");
                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            VisualizzaAllenamentoActivity.this.finish();
                            Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            VisualizzaAllenamentoActivity.this.startActivity(intent1);
                        }
                    });
                    builder1.setNegativeButton("Annulla", null);

                    final AlertDialog dialog1 = builder1.create();
                    dialog1.show();
                } else {
                    VisualizzaAllenamentoActivity.this.finish();
                    Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    VisualizzaAllenamentoActivity.this.startActivity(intent1);
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    //controllo anche che non ci siano modifiche non salvate, altrimenti lo segnalo
    @Override
    public void onBackPressed() {
        if (modificato) {
            AlertDialog.Builder builder = new AlertDialog.Builder(VisualizzaAllenamentoActivity.this);
            builder.setMessage("Le modifiche non verranno salvate. Vuoi tornare indietro?");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    VisualizzaAllenamentoActivity.this.finish();
                    Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    VisualizzaAllenamentoActivity.this.startActivity(intent1);
                }
            });
            builder.setNegativeButton("Annulla", null);

            final AlertDialog dialog1 = builder.create();
            dialog1.show();
        } else {
            VisualizzaAllenamentoActivity.this.finish();
            Intent intent1 = new Intent(VisualizzaAllenamentoActivity.this, ElencoAllenamentiActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            VisualizzaAllenamentoActivity.this.startActivity(intent1);
        }
    }

    private void initView(){
        txtAllSel = findViewById(R.id.txtAllSel);
        txtElemSelA = findViewById(R.id.txtElemSelA);
        txtEmptyAllenamento = findViewById(R.id.txtEmptyAllenamento);
        esAllRecView = findViewById(R.id.esAllRecView);
        fltABAddAllenamento = findViewById(R.id.fltABAddAllenamento);

        adapter = new EsAllenamentoRecViewAdapter(this);

        esAllRecView.setAdapter(adapter);
        esAllRecView.setLayoutManager(new LinearLayoutManager(this));
    }
}