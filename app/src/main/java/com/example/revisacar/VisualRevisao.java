package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.revisacar.controller.AdapterPersonalizado;
import com.example.revisacar.controller.AdapterPersonalizadoRev;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VisualRevisao extends AppCompatActivity {

    FirebaseFirestore database;
    List<String[]> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_revisao);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();

        database.collection("Revisão")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            itemList = new ArrayList<>();
                            int cont = 1;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String data = document.getString("Data");
                                String descricao = (String) document.getString("descricao_revisao");
                                String km_veiculo = (String) document.getString("km_veiculo");
                                String valor_revisao = (String) document.getString("valor_revisao");

                                String id = String.valueOf(cont);
                                String[] dataItem ={id,km_veiculo,data,descricao};
                                itemList.add(dataItem);
                                cont++;

                                Toast.makeText(VisualRevisao.this, "Lido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                            AdapterPersonalizadoRev adapter = new AdapterPersonalizadoRev(VisualRevisao.this,itemList);
                            ListView lista = findViewById(R.id.list_revisao);
                            lista.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else Toast.makeText(VisualRevisao.this, "Erro na Leitura dos Dados", Toast.LENGTH_SHORT).show();
                    }
                });





    }
}