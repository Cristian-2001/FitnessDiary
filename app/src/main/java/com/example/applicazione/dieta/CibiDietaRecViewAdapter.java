package com.example.applicazione.dieta;

import static com.example.applicazione.dieta.ElencoDieteActivity.LIQUIDS;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.example.applicazione.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CibiDietaRecViewAdapter extends RecyclerView.Adapter<CibiDietaRecViewAdapter.ViewHolder> {

    private static final String TAG = "CibiRecViewAdapter";

    private DataBaseCibo dataBaseCibo;

    //elenco degli id dei cibi
    private List<Integer> cibiId = new ArrayList<>();

    //elenco delle quantità inserite
    private List<Double> quantita = new ArrayList<>();

    //elenco dei cibi selezionati
    private List<Cibo> cibi = new ArrayList<>();

    //numero di elementi
    private int numElem;

    //contesto
    private Context mContext;

    public CibiDietaRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCibiId(List<Integer> cibiId) {
        this.cibiId = cibiId;
    }

    public void setQuantita(List<Double> quantita) {
        this.quantita = quantita;
    }

    public void setNumElem(int numElem) {
        this.numElem = numElem;
    }

    @NonNull
    @Override
    public CibiDietaRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtNome.setText(cibi.get(position).getNome());
        holder.txtCategoria.setText(cibi.get(position).getCategoria());

        double energia, lipidi, acidi_grassi, colesterolo, carboidrati, zuccheri, fibre, proteine, sale;
        String categoria = cibi.get(position).getCategoria();
        Double qta = quantita.get(position);
        if (Arrays.asList(LIQUIDS).contains(categoria)) {
            holder.txtQta.setText(qta.toString() + " mL");
        } else {
            holder.txtQta.setText(qta.toString() + " g");
        }

        energia = cibi.get(position).getEnergia() * qta / 100;
        lipidi = cibi.get(position).getLipidi() * qta / 100;
        acidi_grassi = cibi.get(position).getAcidigrassi() * qta / 100;
        colesterolo = cibi.get(position).getColesterolo() * qta / 100;
        carboidrati = cibi.get(position).getCarboidrati() * qta / 100;
        zuccheri = cibi.get(position).getZuccheri() * qta / 100;
        fibre = cibi.get(position).getFibre() * qta / 100;
        proteine = cibi.get(position).getProteine() * qta / 100;
        sale = cibi.get(position).getSale() * qta / 100;

        holder.txtEnergia.setText(String.format("Calorie: %.2f kcal", energia));
        holder.txtLipidi.setText(String.format("Lipidi: %.2f g", lipidi));
        holder.txtProteine.setText(String.format("Proteine: %.2f g", proteine));
        holder.txtCarboidrati.setText(String.format("Carboidrati: %.2f g", carboidrati));
        holder.txtAcidiGrassi.setText(String.format("Acidi Grassi: %.2f g", acidi_grassi));
        holder.txtColesterolo.setText(String.format("Colesterolo: %.2f g", colesterolo));
        holder.txtZuccheri.setText(String.format("Zuccheri: %.2f g", zuccheri));
        holder.txtFibre.setText(String.format("Fibre: %.2f g", fibre));
        holder.txtSale.setText(String.format("Sale: %.2f g", sale));


        if (cibi.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.VISIBLE);
            holder.btnDownArrow.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedRelLayout.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return numElem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView txtNome, txtCategoria, txtEnergia, txtLipidi, txtProteine, txtCarboidrati;
        private ImageView btnDownArrow, btnEdit, btnDelete;
        private TextView txtQta;

        //elementi da visualizzare quando si espande
        private RelativeLayout expandedRelLayout;
        private TextView txtAcidiGrassi, txtColesterolo, txtZuccheri, txtFibre, txtSale;
        private ImageView btnUpArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dataBaseCibo = new DataBaseCibo(mContext);

            parent = itemView.findViewById(R.id.parent);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtEnergia = itemView.findViewById(R.id.txtEnergia);
            txtLipidi = itemView.findViewById(R.id.txtLipidi);
            txtProteine = itemView.findViewById(R.id.txtProteine);
            txtCarboidrati = itemView.findViewById(R.id.txtCarboidrati);

            btnDownArrow = itemView.findViewById(R.id.btnDownArrow);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            txtQta = itemView.findViewById(R.id.txtQta);

            expandedRelLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtAcidiGrassi = itemView.findViewById(R.id.txtAcidiGrassi);
            txtColesterolo = itemView.findViewById(R.id.txtColesterolo);
            txtZuccheri = itemView.findViewById(R.id.txtZuccheri);
            txtFibre = itemView.findViewById(R.id.txtFibre);
            txtSale = itemView.findViewById(R.id.txtSale);
            btnUpArrow = itemView.findViewById(R.id.btnUpArrow);

            for (int id : cibiId) {
                Log.d(TAG, "onCreate: CIAO " + dataBaseCibo.getCiboById(id).toString());
                cibi.add(dataBaseCibo.getCiboById(id));
            }

            btnDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cibo cibo = cibi.get(getAdapterPosition());
                    cibo.setExpanded(!cibo.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnUpArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Cibo cibo = cibi.get(getAdapterPosition());
                    cibo.setExpanded(!cibo.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Inserisci la quantità: ");

                    final EditText input = new EditText(mContext);
                    input.setText(quantita.get(getAdapterPosition()).toString());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    input.setRawInputType(Configuration.KEYBOARD_12KEY);
                    builder.setView(input);

                    builder.setPositiveButton("Modifica", new DialogInterface.OnClickListener() {
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
                                Toast.makeText(mContext, "Inserire la quantità", Toast.LENGTH_SHORT).show();
                            } else {
                                Double quant = Double.parseDouble(input.getText().toString());
                                quantita.set(getAdapterPosition(), quant);
                                dialog.dismiss();
                                notifyItemChanged(getAdapterPosition());
                                VisualizzaDietaActivity.setModificato();
                            }
                        }
                    });
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Eliminare " + cibi.get(getAdapterPosition()).getNome() + " dalla dieta?");

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
        cibiId.remove(position);
        cibi.remove(position);
        quantita.remove(position);
        numElem--;

        notifyItemRemoved(position);
        notifyItemRangeChanged(position, numElem);
        VisualizzaDietaActivity.setModificato();
        if (numElem == 0) {
            VisualizzaDietaActivity.setLast_item();
        }
    }
}
