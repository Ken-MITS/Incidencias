package com.example.incidencia;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.incidencia.DB.IncidenciaDBHelper;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    protected ArrayList<Incidencia> lista;
    private Context context;
    AlertDialog.Builder builder;
    private IncidenciaDBHelper dbHelper;
    private SQLiteDatabase db;
    private ImageView colorStatus;

    public RecyclerViewAdapter(ArrayList<Incidencia> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        final ViewHolder holder = new ViewHolder(view);

        dbHelper= new IncidenciaDBHelper(context);
        db =dbHelper.getWritableDatabase();


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Log.i("provaLog", "position: "+ holder.getAdapterPosition());
                String mensaje = holder.itemView.getResources().getString(R.string.deleteOneDialog);
                mensaje=mensaje +" "+lista.get(holder.getAdapterPosition()).getNom()+"?";
                Log.i("provaLog", "mensaje: "+mensaje);

                builder = new AlertDialog.Builder(context);
                builder.setMessage(mensaje)
                        .setCancelable(true)
                        .setPositiveButton(holder.itemView.getResources().getText(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteFromList(holder.getAdapterPosition());

                                Fragment list = new List();
                                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, list).commit();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog= builder.create();
                alertDialog.setTitle(holder.itemView.getResources().getString(R.string.dialogTitle));
                alertDialog.show();



                Fragment list = new List();
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, list).commit();
                return true;
            }
        });


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


        setStatus(holder, holder.getAdapterPosition());

        Log.i("provaLog", "adapterposition: "+holder.getAdapterPosition());
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
            colorStatus = itemView.findViewById(R.id.colorStatus);
            layout=itemView.findViewById(R.id.frameLayout);
        }
    }

    public void deleteFromList(int position){
        String date = lista.get(position).getDate();
        dbHelper.deleteOne(date);
        lista.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, lista.size());
        db.close();
    }

    public void setStatus(ViewHolder holder, int position){
        int orange = holder.itemView.getResources().getColor(R.color.orange);
        int green = holder.itemView.getResources().getColor(R.color.green);

        String currentStatus = lista.get(position).getStatus();

        if (currentStatus.equals(holder.itemView.getResources().getString(R.string.assigned))) {
            colorStatus.setColorFilter(orange);
        } else if (currentStatus.equals(holder.itemView.getResources().getString(R.string.done))){
            colorStatus.setColorFilter(green);
        }

    }

}
