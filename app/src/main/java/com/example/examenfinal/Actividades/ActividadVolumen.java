package com.example.examenfinal.Actividades;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.examenfinal.R;
import com.example.examenfinal.Adaptador.VolumenAdaptador;
import com.example.examenfinal.clases.Volumen;
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

public class ActividadVolumen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VolumenAdaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumen);
        recyclerView = findViewById(R.id.volumen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        String idRevista = getIntent().getStringExtra("id");
        cargarVolumenes(idRevista);
    }

    private void cargarVolumenes(String idRevista) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + idRevista)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ActividadVolumen.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonResponse = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Volumen>>() {
                    }.getType();
                    List<Volumen> volumenes = gson.fromJson(jsonResponse, listType);

                    runOnUiThread(() -> {
                        adapter = new VolumenAdaptador(volumenes);
                        recyclerView.setAdapter(adapter);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(ActividadVolumen.this, "Fallo al cargar la data", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
}