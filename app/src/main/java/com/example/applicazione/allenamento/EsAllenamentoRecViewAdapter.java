package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.SelezionaEsercizioActivity.ES_ID_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicazione.R;

import java.util.ArrayList;
import java.util.List;

public class EsAllenamentoRecViewAdapter extends RecyclerView.Adapter<EsAllenamentoRecViewAdapter.ViewHolder> {
    private static final String TAG = "EserciziRecViewAdapter";

    //elenco degli esercizi da visualizzare
    private List<Esercizio> esercizi = new ArrayList<>();

    //elenco delle serie
    private List<Integer> serie = new ArrayList<>();

    //elenco delle ripetizioni
    private List<Integer> reps = new ArrayList<>();

    //elenco dei tempi di recupero
    private List<Integer> tRec = new ArrayList<>();

    //contesto
    private Context mContext;

    public EsAllenamentoRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setEsercizi(List<Esercizio> esercizi) {
        this.esercizi = esercizi;
    }

    public void setSerie(List<Integer> serie) {
        this.serie = serie;
    }

    public void setReps(List<Integer> reps) {
        this.reps = reps;
    }

    public void settRec(List<Integer> tRec) {
        this.tRec = tRec;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_esercizio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        holder.txtNomeEser.setText(esercizi.get(position).getNome());
        holder.txtSerieReps.setText(serie.get(position).toString() + " serie da " + reps.get(position).toString() + " ripetizioni ciascuna");
        holder.txtTRec.setText("Tempo di recupero: " + tRec.get(position).toString() + " secondi");
        holder.txtGruppoMuscEser.setText("Gruppo muscolare: " + esercizi.get(position).getGruppoMuscolare());
        holder.txtParteCorpoEser.setText("Parte del corpo: " + esercizi.get(position).getParteDelCorpo());
        holder.txtModEser.setText("Modalità: " + esercizi.get(position).getModalita());

        //nascondo gli elementi da non visualizzare
        holder.txtDiffEser.setVisibility(View.GONE);
        holder.txtTipoEser.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return esercizi.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtNomeEser, txtGruppoMuscEser, txtDiffEser, txtParteCorpoEser, txtTipoEser, txtModEser,
                txtSerieReps, txtTRec;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNomeEser = itemView.findViewById(R.id.txtNomeEser);
            txtGruppoMuscEser = itemView.findViewById(R.id.txtGruppoMuscEser);
            txtDiffEser = itemView.findViewById(R.id.txtDiffEser);
            txtParteCorpoEser = itemView.findViewById(R.id.txtParteCorpoEser);
            txtTipoEser = itemView.findViewById(R.id.txtTipoEser);
            txtModEser = itemView.findViewById(R.id.txtModEser);

            txtSerieReps = itemView.findViewById(R.id.txtSerieReps);
            txtTRec = itemView.findViewById(R.id.txtTRec);
        }
    }
}
