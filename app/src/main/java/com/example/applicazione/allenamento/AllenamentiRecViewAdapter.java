package com.example.applicazione.allenamento;

import static com.example.applicazione.allenamento.VisualizzaAllenamentoActivity.ALLENAMENTO_ID_KEY;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
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

    private int position;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return allenamenti.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private CardView parent;
        private TextView txtNomeAll, txtNumElemAll;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            txtNomeAll = itemView.findViewById(R.id.txtNomeALl);
            txtNumElemAll = itemView.findViewById(R.id.txtNumElemAll);

            parent.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            //menuInfo is null
            menu.add(Menu.NONE, R.id.elimina,
                    Menu.NONE, R.string.elimina_allenamento);
        }
    }
}
