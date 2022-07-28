package com.example.applicazione;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AllenamentiRecViewAdapter extends RecyclerView.Adapter<AllenamentiRecViewAdapter.ViewHolder> {
    private static final String TAG = "AllenamentiRecViewAdapt";

    //elenco degli allenamenti
    private ArrayList<Allenamento> allenamenti = new ArrayList<>();

    //contesto
    private Context mContext;

    public AllenamentiRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_allenamento, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBinfViewHolder: Called");
        holder.txtAllenamento.setText(allenamenti.get(position).getNome());
        Glide.with(mContext)
                .asBitmap()
                .load(allenamenti.get(position).getImageUrl)
                .into(holder.imgAllenamento);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, allenamenti.get(position).getNome() + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return allenamenti.size();
    }

    public void setAllenamenti(ArrayList<Allenamento> allenamenti) {
        this.allenamenti = allenamenti;

        //notifico quando ci sono dei cambiamenti all'array
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgAllenamento;
        private TextView txtAllenamento;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgAllenamento = itemView.findViewById(R.id.imgAllenamento);
            txtAllenamento = itemView.findViewById(R.id.txtAllenamento);
        }
    }
}
