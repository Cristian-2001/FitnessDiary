package com.example.applicazione;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class DieteRecViewAdapter extends RecyclerView.Adapter<DieteRecViewAdapter.ViewHolder> {
    private final static String TAG = "DieteRecViewAdapter";

    //elenco diete
    private ArrayList<Dieta> diete = new ArrayList<>();

    //contesto
    private Context mContext;

    public DieteRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDiete(ArrayList<Dieta> diete) {
        this.diete = diete;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dieta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtNomeDieta.setText(diete.get(position).getNome());
        holder.txtNumElem.setText(diete.get(position).getNumElem().toString() + " elementi");

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, diete.get(position).getNome() + " cliccata", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diete.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private TextView txtNomeDieta, txtNumElem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNomeDieta = itemView.findViewById(R.id.txtNomeDieta);
            txtNumElem = itemView.findViewById(R.id.txtNumElem);
        }
    }
}
