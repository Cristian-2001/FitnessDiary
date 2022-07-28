package com.example.applicazione;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ElencoAllenamentiActivity extends AppCompatActivity {

    private RecyclerView allenamentiRecView;
    private AllenamentiRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_allenamenti);

        //creo un nuovo adapter e una nuova RecView
        adapter = new AllenamentiRecViewAdapter(this);
        allenamentiRecView = findViewById(R.id.allenamentiRecView);

        allenamentiRecView.setAdapter(adapter);
        allenamentiRecView.setLayoutManager(new LinearLayoutManager(this));

        //creo un'ArrayList di allenamenti
        /*ArrayList<Allenamento> allenamenti = new ArrayList<>();
        allenamenti.add(new Allenamento(1, "Piegamenti", 500,
                "https://www.invictusarena.com/it/wp-content/uploads/2020/03/piegamenti-sulle-braccia-1.jpg"));
        allenamenti.add(new Allenamento(2, "Squat", 420,
                "https://www.staiinforma.com/wp-content/uploads/2018/07/Squat-a-corpo-libero.jpg"));

        adapter.setAllenamenti(allenamenti);*/
    }
}