package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.revisacar.controller.AdapterPersonalizado;
import com.example.revisacar.controller.AdapterPersonalizadoRev;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VisualRevisao extends AppCompatActivity {

    FirebaseFirestore database;
    List<String[]> itemList;
    ImageView fechar;
    AdapterPersonalizadoRev adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_revisao);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();
        fechar = findViewById(R.id.fechar);
        carregarDB();

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualRevisao.this,MeuVeiculo.class);
                startActivity(intent);
            }
        });

    }

    private void carregarDB(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.collection("Revisão")
                .whereEqualTo("userId",userId)
                .orderBy("timestamp", Query.Direction.ASCENDING)
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
                                String idDB = document.getId();

                                String id = String.valueOf(cont);
                                String[] dataItem ={id,km_veiculo,data,descricao,valor_revisao,idDB};
                                itemList.add(dataItem);
                                cont++;

                                Toast.makeText(VisualRevisao.this, "Lido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                            adapter = new AdapterPersonalizadoRev(VisualRevisao.this,itemList);
                            ListView lista = findViewById(R.id.list_revisao);
                            lista.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            registerForContextMenu(lista);

                        }else Toast.makeText(VisualRevisao.this, "Erro na Leitura dos Dados", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.list_revisao){
            menu.add("Excluir");
        }
        if (v.getId() == R.id.list_revisao){
            menu.add("Alterar");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("Excluir")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            String idItem = itemList.get(position)[5];

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Deseja mesmo excluir o registro?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    database.collection("Revisão")
                            .document(idItem)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(VisualRevisao.this, "Excluído com sucesso", Toast.LENGTH_SHORT).show();
                                    itemList.remove(position);
                                    adapter.notifyDataSetChanged();
                                    carregarDB();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(VisualRevisao.this, "Erro ao excluir o item: " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            });
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();

            return true;
        }

        if(item.getTitle().equals("Alterar")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;



            Intent intent = new Intent(this,EditarItemRev.class);
            intent.putExtra("idItem",itemList.get(position)[5]);
            intent.putExtra("km_veiculo",itemList.get(position)[1]);
            intent.putExtra("data",itemList.get(position)[2]);
            intent.putExtra("descricao",itemList.get(position)[3]);
            intent.putExtra("valor_revisao",itemList.get(position)[4]);
            startActivity(intent);

        }




        return super.onContextItemSelected(item);
    }
}