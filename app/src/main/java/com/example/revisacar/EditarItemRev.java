package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.revisacar.controller.ObterData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditarItemRev extends AppCompatActivity {

    private EditText edit_data_revisao,edit_km_veiculo,edit_valor_revisao,edit_descricao_revisao;
    private AppCompatButton btn_alterar_rev;
    private FirebaseFirestore database;
    private String idItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item_rev);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        idItem = intent.getStringExtra("idItem");
        edit_data_revisao = findViewById(R.id.data_revisao);
        edit_km_veiculo = findViewById(R.id.km_veiculo);
        edit_valor_revisao = findViewById(R.id.valor_revisao);
        edit_descricao_revisao = findViewById(R.id.descricao_revisao);
        btn_alterar_rev = findViewById(R.id.btn_alterar_rev);
        database = FirebaseFirestore.getInstance();

        edit_data_revisao.setText(intent.getStringExtra("data"));
        edit_km_veiculo.setText(intent.getStringExtra("km_veiculo"));
        edit_valor_revisao.setText(intent.getStringExtra("valor_revisao"));
        edit_descricao_revisao.setText(intent.getStringExtra("descricao"));

        edit_data_revisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObterData obterData = new ObterData(edit_data_revisao);
                obterData.show(getSupportFragmentManager(),"seletor_data");
            }
        });

        btn_alterar_rev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_revisao,km_veiculo,valor_revisao,descricao_revisao;
                data_revisao = edit_data_revisao.getText().toString();
                km_veiculo = edit_km_veiculo.getText().toString();
                valor_revisao = edit_valor_revisao.getText().toString();
                descricao_revisao = edit_descricao_revisao.getText().toString();

                Map<String,Object> revisao = new HashMap<>();
                revisao.put("Data",data_revisao);
                revisao.put("km_veiculo",km_veiculo);
                revisao.put("valor_revisao",valor_revisao);
                revisao.put("descricao_revisao",descricao_revisao);
                //revisao.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());

                database.collection("Revisão")
                        .document(idItem)
                        .update(revisao)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditarItemRev.this, "Dados Atualizados com Sucesso!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditarItemRev.this,VisualRevisao.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditarItemRev.this, "Erro ao atualizar dados!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

    }
}