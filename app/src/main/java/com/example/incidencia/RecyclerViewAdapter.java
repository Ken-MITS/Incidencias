package com.example.incidencia;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    protected ArrayList<Incidencia> lista;
    private Context context;


    public RecyclerViewAdapter(ArrayList<Incidencia> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Test click"+String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_SHORT).show();
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Fragment detalle = new Detalle(holder.getAdapterPosition());
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, detalle).addToBackStack(null).commit();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nivel.setText(lista.get(position).getPrioritat());
        holder.nombre.setText(lista.get(position).getNom());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombre, nivel;
        ConstraintLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre= itemView.findViewById(R.id.TVnombre);
            nivel= itemView.findViewById(R.id.TVnivel);

            layout=itemView.findViewById(R.id.frameLayout);
        }
    }

}
