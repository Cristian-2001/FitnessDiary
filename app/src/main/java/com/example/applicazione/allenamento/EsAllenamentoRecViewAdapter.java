package com.example.applicazione.allenamento;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applicazione.R;

import java.util.ArrayList;
import java.util.List;

public class EsAllenamentoRecViewAdapter extends RecyclerView.Adapter<EsAllenamentoRecViewAdapter.ViewHolder> {
    private static final String TAG = "EserciziRecViewAdapter";

    //elenco degli ID degli esercizi da visualizzare
    private List<Integer> eserciziId = new ArrayList<>();

    //elenco degli esercizi da visualizzare
    private List<Esercizio> esercizi = new ArrayList<>();

    //elenco delle serie
    private List<Integer> serie = new ArrayList<>();

    //elenco delle ripetizioni
    private List<Integer> reps = new ArrayList<>();

    //elenco dei tempi di recupero
    private List<Integer> tRec = new ArrayList<>();

    //numero di elementi
    private int numElem;

    //contesto
    private Context mContext;

    public EsAllenamentoRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setEserciziId(List<Integer> eserciziId) {
        this.eserciziId = eserciziId;
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

    public void setNumElem(int numElem) {
        this.numElem = numElem;
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
        return numElem;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView txtNomeEser, txtGruppoMuscEser, txtDiffEser, txtParteCorpoEser, txtTipoEser, txtModEser,
                txtSerieReps, txtTRec;
        private ImageView btnEdit, btnDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNomeEser = itemView.findViewById(R.id.txtNomeEser);
            txtGruppoMuscEser = itemView.findViewById(R.id.txtGruppoMuscEser);
            txtDiffEser = itemView.findViewById(R.id.txtDiffEser);
            txtParteCorpoEser = itemView.findViewById(R.id.txtParteCorpoEser);
            txtTipoEser = itemView.findViewById(R.id.txtTipoEser);
            txtModEser = itemView.findViewById(R.id.txtModEser);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            txtSerieReps = itemView.findViewById(R.id.txtSerieReps);
            txtTRec = itemView.findViewById(R.id.txtTRec);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    //builder.setMessage("Inserire numero di serie, numero di ripetizioni e tempo di recupero in secondi: ");

                    //creo il layout per gli EditText e li aggiungo con i rispettivi commenti
                    LinearLayout layout = new LinearLayout(mContext);
                    layout.setOrientation(LinearLayout.VERTICAL);

                    final TextView txtSerie = new TextView(mContext);
                    txtSerie.setText("Inserisci il numero di serie: ");
                    layout.addView(txtSerie);
                    final EditText edtSerie = new EditText(mContext);
                    edtSerie.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edtSerie.setRawInputType(Configuration.KEYBOARD_12KEY);
                    layout.addView(edtSerie);

                    final TextView txtReps = new TextView(mContext);
                    txtReps.setText("\nInserisci il numero di ripetizioni: ");
                    layout.addView(txtReps);
                    final EditText edtReps = new EditText(mContext);
                    edtReps.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edtReps.setRawInputType(Configuration.KEYBOARD_12KEY);
                    layout.addView(edtReps);

                    final TextView txtTRec = new TextView(mContext);
                    txtTRec.setText("\nInserisci il tempo di recupero in secondi: ");
                    layout.addView(txtTRec);
                    final EditText edtTRec = new EditText(mContext);
                    edtTRec.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    edtTRec.setRawInputType(Configuration.KEYBOARD_12KEY);
                    layout.addView(edtTRec);

                    builder.setView(layout);

                    builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //non faccio nulla perché faccio l'override più avanti
                        }
                    });

                    builder.setNegativeButton("Annulla", null);

                    final AlertDialog dialog = builder.create();
                    dialog.setView(layout, 60, 60, 60, 0);
                    dialog.show();

                    dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int newSerie, newReps, newTRec;
                            if (edtSerie.getText().toString().equals("")) {
                                newSerie = serie.get(getAdapterPosition());
                            } else {
                                newSerie = Integer.parseInt(edtSerie.getText().toString());
                            }

                            if (edtReps.getText().toString().equals("")) {
                                newReps = reps.get(getAdapterPosition());
                            } else {
                                newReps = Integer.parseInt(edtReps.getText().toString());
                            }

                            if (edtTRec.getText().toString().equals("")) {
                                newTRec = tRec.get(getAdapterPosition());
                            } else {
                                newTRec = Integer.parseInt(edtTRec.getText().toString());
                            }

                            serie.set(getAdapterPosition(), newSerie);
                            reps.set(getAdapterPosition(), newReps);
                            tRec.set(getAdapterPosition(), newTRec);
                            dialog.dismiss();
                            notifyItemChanged(getAdapterPosition());
                            VisualizzaAllenamentoActivity.setModificato();
                        }
                    });
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Eliminare " + esercizi.get(getAdapterPosition()).getNome() + " dall'allenamento?");

                    builder.setPositiveButton("Elimina", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeAt(getAdapterPosition());
                        }
                    });

                    builder.setNegativeButton("Annulla", null);
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }
    }

    private void removeAt(int position) {
        eserciziId.remove(position);
        esercizi.remove(position);
        serie.remove(position);
        reps.remove(position);
        tRec.remove(position);
        numElem--;

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, numElem);
        VisualizzaAllenamentoActivity.setModificato();
        if (numElem == 0) {
            VisualizzaAllenamentoActivity.setLast_item();
        }
    }
}
