package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

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

public class AllenamentiRecViewAdapter extends RecyclerView.Adapter<AllenamentiRecViewAdapter.ViewHolder> {
    private static final String TAG = "AllenamentiRecViewAdapt";

    //elenco degli allenamenti
    private List<Allenamento> allenamenti = new ArrayList<>();

    //contesto
    private Context mContext;

    public AllenamentiRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setAllenamenti(List<Allenamento> allenamenti) {
        this.allenamenti = allenamenti;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_allenamento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtNomeAll.setText(allenamenti.get(position).getNome());
        if (allenamenti.get(position).getNumElem() == 1) {
            holder.txtNumElemAll.setText(allenamenti.get(position).getNumElem().toString() + " elemento");
        } else {
            holder.txtNumElemAll.setText(allenamenti.get(position).getNumElem().toString() + " elementi");
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VisualizzaAllenamentoActivity.class);
                intent.putExtra(ALLENAMENTO_ID_KEY, allenamenti.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allenamenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView txtNomeAll, txtNumElemAll;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNomeAll = itemView.findViewById(R.id.txtNomeALl);
            txtNumElemAll = itemView.findViewById(R.id.txtNumElemAll);
        }
    }
}
