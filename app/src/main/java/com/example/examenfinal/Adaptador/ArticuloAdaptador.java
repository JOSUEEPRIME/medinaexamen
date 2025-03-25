package com.example.examenfinal.Adaptador;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.examenfinal.R;
import com.example.examenfinal.clases.Articulo;
import com.example.examenfinal.clases.Autor;
import com.example.examenfinal.clases.Galleys;
import com.example.examenfinal.clases.Galleys;

import java.util.List;

public class ArticuloAdaptador extends RecyclerView.Adapter<ArticuloAdaptador.ViewHolder> {
    private List<Articulo> listadoArticulos;

    public ArticuloAdaptador(List<Articulo> articleList) {
        this.listadoArticulos = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_articulo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Articulo article = listadoArticulos.get(position);
        holder.titulo.setText(article.getTitle());

        StringBuilder authors = new StringBuilder();
        for (Autor author : article.getAuthors()) {
            authors.append(author.getNombres()).append(" ").append(author.getFiliacion()).append("), ");
        }
        holder.autores.setText(authors.toString());
        holder.fechaPublicacion.setText(article.getDatePublished());
        holder.html.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getDoi()));
            v.getContext().startActivity(browserIntent);
        });
        for (Galleys galley : article.getGaleys()) {
            if (galley.getLabel().equals("PDF")) {
                holder.pdf.setOnClickListener(v -> {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galley.getUrlViewGalleys()));
                    v.getContext().startActivity(browserIntent);
                });
            }
        }

        holder.descargar.setOnClickListener(v -> {
            for (Galleys galley : article.getGaleys()) {
                if (galley.getLabel().equals("PDF")) {
                    String nombreArticulo = article.getTitle().replace(" ", "_").replace("/", "_").replace(":", "_");
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(galley.getUrlViewGalleys().replace("view", "download")));
                    request.setTitle(nombreArticulo);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, nombreArticulo + ".pdf");
                    DownloadManager downloadManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                    downloadManager.enqueue(request);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listadoArticulos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView autores;
        public Button html;
        public Button pdf;
        public ImageView descargar;
        public TextView fechaPublicacion;

        public ViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.titulo_articulo);
            fechaPublicacion = itemView.findViewById(R.id.fecha_articulo);
            autores = itemView.findViewById(R.id.autores);
            html = itemView.findViewById(R.id.boton_html);
            descargar = itemView.findViewById(R.id.descargar);
            pdf = itemView.findViewById(R.id.boton_pdf);
        }
    }

}
