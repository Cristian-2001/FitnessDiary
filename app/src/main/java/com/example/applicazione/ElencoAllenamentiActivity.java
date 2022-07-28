package com.example.applicazione;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ElencoAllenamentiActivity extends AppCompatActivity {

    private RecyclerView allenamentiRecView;
    private AllenamentiRecViewAdapter adapter;
    private FloatingActionButton fltABAddAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_allenamenti);

        initView();

        //creo un OnClickListener per il pulsante (apre l'Activity per aggiungere un allenamento)
        fltABAddAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElencoAllenamentiActivity.this, AggiungiAllenamentoActivity.class);
                ElencoAllenamentiActivity.this.startActivity(intent);
            }
        });

        //creo un'ArrayList di allenamenti
        /*ArrayList<Allenamento> allenamenti = new ArrayList<>();
        allenamenti.add(new Allenamento(1, "Piegamenti", 500,
                "https://www.invictusarena.com/it/wp-content/uploads/2020/03/piegamenti-sulle-braccia-1.jpg"));
        allenamenti.add(new Allenamento(2, "Squat", 420,
                "https://www.staiinforma.com/wp-content/uploads/2018/07/Squat-a-corpo-libero.jpg"));

        adapter.setAllenamenti(allenamenti);*/
    }

    private void initView() {
        //creo un nuovo adapter e una nuova RecView e li inizializzo
        adapter = new AllenamentiRecViewAdapter(this);
        allenamentiRecView = findViewById(R.id.allenamentiRecView);

        allenamentiRecView.setAdapter(adapter);
        allenamentiRecView.setLayoutManager(new LinearLayoutManager(this));

        //inizializzo il Floating Action Button
        fltABAddAll = findViewById(R.id.fltABAddAll);
    }
}