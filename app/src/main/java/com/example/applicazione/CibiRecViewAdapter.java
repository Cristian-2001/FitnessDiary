package com.example.applicazione;

import static com.example.applicazione.SelezionaCiboActivity.CIBO_ID_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CibiRecViewAdapter extends RecyclerView.Adapter<CibiRecViewAdapter.ViewHolder>{



    private static final String TAG = "CibiRecViewAdapter";

    //elenco dei cibi selezionati
    private ArrayList<Cibo> cibi = new ArrayList<>();

    //contesto
    private Context mContext;

    public CibiRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCibi(ArrayList<Cibo> cibi) {
        this.cibi = cibi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cibo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {




        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtNome.setText(cibi.get(position).getNome());
        holder.txtCategoria.setText(cibi.get(position).getCategoria());
        holder.txtEnergia.setText("Calorie: " + cibi.get(position).getEnergia().toString() + " kcal");
        holder.txtLipidi.setText("Lipidi: " + cibi.get(position).getLipidi().toString() + " g");
        holder.txtProteine.setText("Proteine: " + cibi.get(position).getProteine().toString() + " g");
        holder.txtCarboidrati.setText("Carboidrati: " + cibi.get(position).getCarboidrati().toString() + " g");

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SelezionaCiboActivity.class);
                intent.putExtra(CIBO_ID_KEY, cibi.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cibi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parent;
        private TextView txtNome, txtCategoria, txtEnergia, txtLipidi, txtProteine, txtCarboidrati;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNome = itemView.findViewById(R.id.txtNome);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtEnergia = itemView.findViewById(R.id.txtEnergia);
            txtLipidi = itemView.findViewById(R.id.txtLipidi);
            txtProteine = itemView.findViewById(R.id.txtProteine);
            txtCarboidrati = itemView.findViewById(R.id.txtCarboidrati);
        }
    }
}
