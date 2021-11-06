package com.example.r6.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.r6.R;
import com.example.r6.VerDefensores;
import com.example.r6.VerOperador;
import com.example.r6.entidades.Operadores;

import java.util.ArrayList;

public class listaOperadoresAdapter extends RecyclerView.Adapter<listaOperadoresAdapter.OperadorViewHolder> {

    ArrayList<Operadores> listaOperadores;
    public listaOperadoresAdapter(ArrayList<Operadores> listaOperadores){
        this.listaOperadores = listaOperadores;
    }
    @NonNull
    @Override
    public listaOperadoresAdapter.OperadorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_operador,null ,false);
        return new OperadorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listaOperadoresAdapter.OperadorViewHolder holder, int position) {
        holder.viewApodo.setText(listaOperadores.get(position).getApodo());
        holder.viewORG.setText(listaOperadores.get(position).getORG());
        holder.imageFoto.setImageResource(R.drawable.jackal);

    }

    @Override
    public int getItemCount() {
        return listaOperadores.size();

    }

    public class OperadorViewHolder extends RecyclerView.ViewHolder {
        TextView viewApodo,viewORG,viweNombre,ViewHabilidad,viewTipo;
        ImageView imageFoto;
        public OperadorViewHolder(@NonNull View itemView) {
            super(itemView);
            viewApodo = itemView.findViewById(R.id.textApodo);
            viewORG = itemView.findViewById(R.id.textORG);
            imageFoto = itemView.findViewById(R.id.imageOperadorL);
            viweNombre = itemView.findViewById(R.id.textView15);
            ViewHabilidad = itemView.findViewById(R.id.textView14);
            viewTipo = itemView.findViewById(R.id.textView17);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerOperador.class);
                    intent.putExtra("ID",listaOperadores.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });

        }
    }
}
