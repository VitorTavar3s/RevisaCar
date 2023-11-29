package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.revisacar.controller.AdapterPersonalizado;
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

public class VisualAbastecer extends AppCompatActivity {

    FirebaseFirestore database;
    List<String[]> itemList;
    AdapterPersonalizado adapter;
    ImageView fechar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_abastecer);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();
        fechar = findViewById(R.id.fechar);
        carregarDB();

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualAbastecer.this,MeuVeiculo.class);
                startActivity(intent);
            }
        });

    };

    private void carregarDB(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.collection("Abastecimento")
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
                                String km_veiculo = document.getString("km_veiculo");
                                String tipo_combustivel = document.getString("tipo_combustivel");
                                String valor_litro = document.getString("valor_litro");
                                String valor_total = document.getString("valor_total");
                                String idDB = document.getId();

                                String id = String.valueOf(cont);
                                String[] dataItem ={id,data,km_veiculo,tipo_combustivel,valor_litro,valor_total,idDB};
                                itemList.add(dataItem);
                                cont++;

                                Toast.makeText(VisualAbastecer.this, "Lido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                            adapter = new AdapterPersonalizado(VisualAbastecer.this,itemList);
                            ListView lista = findViewById(R.id.list_abastecer);
                            lista.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            registerForContextMenu(lista);


                        }else Toast.makeText(VisualAbastecer.this, "Erro na Leitura dos Dados", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.list_abastecer){
            menu.add("Excluir");
        }
        if (v.getId() == R.id.list_abastecer){
            menu.add("Alterar");
        }

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals("Excluir")){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int position = info.position;
            String idItem = itemList.get(position)[6];

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Excluir");
            builder.setIcon(R.drawable.ic_delete);
            builder.setMessage("Deseja mesmo excluir o registro?");
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    database.collection("Abastecimento")
                            .document(idItem)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(VisualAbastecer.this, "Excluído com sucesso!", Toast.LENGTH_SHORT).show();
                                    itemList.remove(position);
                                    adapter.notifyDataSetChanged();
                                    carregarDB();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(VisualAbastecer.this, "Erro ao excluir o item: " + e.getMessage(), Toast.LENGTH_SHORT).show();

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



            Intent intent = new Intent(this,EditarItem.class);
            intent.putExtra("idItem",itemList.get(position)[6]);
            intent.putExtra("data",itemList.get(position)[1]);
            intent.putExtra("km_veiculo",itemList.get(position)[2]);
            intent.putExtra("tipo_combustivel",itemList.get(position)[3]);
            intent.putExtra("valor_litro",itemList.get(position)[4]);
            intent.putExtra("valor_total",itemList.get(position)[5]);
            startActivity(intent);

        }

        return super.onContextItemSelected(item);
    }

}

