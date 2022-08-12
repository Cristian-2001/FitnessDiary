package com.example.applicazione;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AggiungiDietaActivity extends AppCompatActivity {

    private static final String TAG = "AggiungiDietaActivity";

    private Spinner spnCategoria;
    private Button btnCerca;
    private RecyclerView cibiRecView;
    private CibiRecViewAdapter adapter;
    private ArrayList<Cibo> cibi = new ArrayList<>();
    private EditText edtTxtCercaNome;
    private TextView txtEmpty;

    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggiungi_dieta);

        initView();

        dataBaseHelper = new DataBaseHelper(AggiungiDietaActivity.this);
        cibi = dataBaseHelper.getAllCibi();
        adapter.setCibi(cibi);

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
                    cibi = dataBaseHelper.getAllCibi();
                } else {
                    Log.d(TAG, "onClick: ECCOMI");
                    cibi = dataBaseHelper.getCibiByCatNome(cat, nome);
                }

                adapter.setCibi(cibi);
                cibiRecView.setAdapter(adapter);

                if (adapter.getItemCount() == 0) {
                    txtEmpty.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    //creo e inizializzo tutti gli elementi dell'Activity
    public void initView() {
        adapter = new CibiRecViewAdapter(this);
        spnCategoria = findViewById(R.id.spnCategoria);
        btnCerca = findViewById(R.id.btnCerca);
        cibiRecView = findViewById(R.id.cibiRecView);
        edtTxtCercaNome = findViewById(R.id.edtTxtCercaNome);
        txtEmpty = findViewById(R.id.txtEmpty);


        cibiRecView.setAdapter(adapter);
        cibiRecView.setLayoutManager(new GridLayoutManager(this, 2));
    }
}