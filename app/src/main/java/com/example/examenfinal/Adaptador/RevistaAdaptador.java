package com.example.examenfinal.Adaptador;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examenfinal.R;
import com.example.examenfinal.Actividades.ActividadVolumen;
import com.example.examenfinal.clases.Revista;

import java.util.List;

public class RevistaAdaptador extends RecyclerView.Adapter<RevistaAdaptador.ViewHolder> {

    private List<Revista> listadoRevistas;

    public RevistaAdaptador(List<Revista> journalList) {
        this.listadoRevistas = journalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revista, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Revista revista = listadoRevistas.get(position);
        holder.titulo.setText(revista.getName());
        holder.descripcion.setText(Html.fromHtml(revista.getDescription(), Html.FROM_HTML_MODE_LEGACY));
        Glide.with(holder.portada.getContext())
                .load(revista.getPortada())
                .into(holder.portada);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ActividadVolumen.class);
            intent.putExtra("id", revista.getJournal_id());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listadoRevistas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;
        public TextView descripcion;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = itemView.findViewById(R.id.portada);
            titulo = itemView.findViewById(R.id.titulo);
            descripcion = itemView.findViewById(R.id.descripcion);
        }
    }
}
