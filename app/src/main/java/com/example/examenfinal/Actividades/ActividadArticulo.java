package com.example.examenfinal.Actividades;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.examenfinal.R;
import com.example.examenfinal.Adaptador.ArticuloAdaptador;

import com.example.examenfinal.clases.Articulo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActividadArticulo extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArticuloAdaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulo);
        recyclerView = findViewById(R.id.recycler_Articulos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String idVolumen = getIntent().getStringExtra("id");
        cargarArticulos(idVolumen);
    }

    public void cargarArticulos(String volumenId){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + volumenId)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ActividadArticulo.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Articulo>>() {
                    }.getType();
                    List<Articulo> articulos = gson.fromJson(jsonResponse, listType);

                    runOnUiThread(() -> {
                        adapter = new ArticuloAdaptador(articulos);
                        recyclerView.setAdapter(adapter);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ActividadArticulo.this, "Failed to load data", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}