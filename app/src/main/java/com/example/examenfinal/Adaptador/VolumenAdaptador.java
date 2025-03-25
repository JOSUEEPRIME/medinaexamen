package com.example.examenfinal.Adaptador;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.examenfinal.R;
import com.example.examenfinal.Actividades.ActividadArticulo;
import com.example.examenfinal.clases.Volumen;

import java.util.List;

public class VolumenAdaptador extends RecyclerView.Adapter<VolumenAdaptador.ViewHolder> {
    private List<Volumen> listadoVolumenes;

    public VolumenAdaptador(List<Volumen> volumeList) {
        this.listadoVolumenes = volumeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_volumen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Volumen volumen = listadoVolumenes.get(position);
        holder.titulo.setText(volumen.getTitle());
        holder.volumenNumero.setText("Volumen: " + volumen.getVolume() + ", Número: " + volumen.getNumber());
        holder.anoPublicacion.setText("Año de Publicación: " + volumen.getYear());

        Glide.with(holder.portada.getContext())
                .load(volumen.getCover())
                .into(holder.portada);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ActividadArticulo.class);
            intent.putExtra("id", volumen.getIssue_id());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listadoVolumenes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView portada;
        public TextView titulo;
        public TextView volumenNumero;
        public TextView anoPublicacion;

        public ViewHolder(View itemView) {
            super(itemView);
            portada = itemView.findViewById(R.id.portada_volumen);
            titulo = itemView.findViewById(R.id.titulo_volumen);
            volumenNumero = itemView.findViewById(R.id.volumen_numero);
            anoPublicacion = itemView.findViewById(R.id.ano_publicacion);
        }
    }
}
