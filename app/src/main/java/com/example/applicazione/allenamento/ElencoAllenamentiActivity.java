package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ALLENAMENTO_NOME;
import static com.example.applicazione.allenamento.AggiungiAllenamentoActivity.ELENCO_ALL;
import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicazione.MainActivity;
import com.example.applicazione.R;
import com.example.applicazione.dieta.ElencoDieteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ElencoAllenamentiActivity extends AppCompatActivity {
    private static final String TAG = "ElencoAllActivity";

    private FloatingActionButton fltABAddAll;
    private TextView txtEmptyAll;

    private RecyclerView allenamentiRecView;
    private AllenamentiRecViewAdapter adapter;
    private List<Allenamento> allenamenti;

    private String nomeAllenamento;

    private DataBaseAllenamento dataBaseAllenamento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_allenamenti);

        //chiamo l'action bar
        ActionBar actionBar = getSupportActionBar();

        //mostro il back button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        initView();

        dataBaseAllenamento = new DataBaseAllenamento(ElencoAllenamentiActivity.this);

        allenamenti = dataBaseAllenamento.getAllAllenamenti();

        adapter.setAllenamenti(allenamenti);

        if (adapter.getItemCount() == 0) {
            txtEmptyAll.setVisibility(View.VISIBLE);
        }

        //registro la RecView per un ContextMenu
        registerForContextMenu(allenamentiRecView);

        //creo un OnClickListener per il pulsante (apre l'Activity per aggiungere un allenamento)
        fltABAddAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ElencoAllenamentiActivity.this);
                builder.setMessage("Inserisci il nome dell'allenamento: ");

                final EditText input = new EditText(ElencoAllenamentiActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                builder.setView(input);

                builder.setPositiveButton("Avanti", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //non faccio nulla perché faccio l'override più avanti
                    }
                });

                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.setView(input, 60, 0, 60, 0);
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (input.getText().toString().equals("")) {
                            Toast.makeText(ElencoAllenamentiActivity.this, "Inserire il nome dell'allenamento", Toast.LENGTH_SHORT).show();
                        } else {
                            nomeAllenamento = input.getText().toString();

                            Intent intent = new Intent(ElencoAllenamentiActivity.this, AggiungiAllenamentoActivity.class);

                            intent.putExtra(ALLENAMENTO_NOME, nomeAllenamento);
                            intent.putExtra(ELENCO_ALL, 1);
                            intent.putExtra(ALLENAMENTO_ID_KEY, -2);
                            ElencoAllenamentiActivity.this.startActivity(intent);
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void initView() {
        txtEmptyAll = findViewById(R.id.txtEmptyAll);

        //creo un nuovo adapter e una nuova RecView e li inizializzo
        adapter = new AllenamentiRecViewAdapter(this);
        allenamentiRecView = findViewById(R.id.allenamentiRecView);

        allenamentiRecView.setAdapter(adapter);
        allenamentiRecView.setLayoutManager(new LinearLayoutManager(this));

        //inizializzo il Floating Action Button
        fltABAddAll = findViewById(R.id.fltABAddAll);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Intent intent = new Intent(ElencoAllenamentiActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                ElencoAllenamentiActivity.this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //faccio in modo che quando clicco per tornare indietro, lo stack delle activity venga pulito
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * creo e setto il ContextMenu per eliminare l'allenamento
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = -1;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case R.id.elimina:
                // do your stuff
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Eliminare la dieta " + dataBaseAllenamento.getAllenamentoById(allenamenti.get(adapter.getPosition()).getId()).getNome() + "?");
                builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ElencoAllenamentiActivity.this.finish();
                        startActivity(getIntent());
                        dataBaseAllenamento.eliminaAllenamento(allenamenti.get(adapter.getPosition()).getId());
                    }
                });
                builder.setNegativeButton("Annulla", null);

                final AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}