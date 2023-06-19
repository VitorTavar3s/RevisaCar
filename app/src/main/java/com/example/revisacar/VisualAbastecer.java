package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.revisacar.controller.AdapterPersonalizado;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VisualAbastecer extends AppCompatActivity {

    FirebaseFirestore database;
    List<String[]> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_abastecer);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();



        database.collection("Abastecimento")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            itemList = new ArrayList<>();
                            int cont = 1;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String data = document.getString("Data");
                                String km_veiculo = (String) document.getString("km_veiculo");
                                String tipo_combustivel = (String) document.getString("tipo_combustivel");
                                String valor_litro = (String) document.getString("valor_litro");
                                String valor_total = (String) document.getString("valor_total");

                                    String id = String.valueOf(cont);
                                    String[] dataItem ={id,data,km_veiculo,tipo_combustivel,valor_litro,valor_total};
                                    itemList.add(dataItem);
                                    cont++;

                                Toast.makeText(VisualAbastecer.this, "Lido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                            AdapterPersonalizado adapter = new AdapterPersonalizado(VisualAbastecer.this,itemList);
                            ListView lista = findViewById(R.id.list_abastecer);
                            lista.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else Toast.makeText(VisualAbastecer.this, "Erro na Leitura dos Dados", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}

