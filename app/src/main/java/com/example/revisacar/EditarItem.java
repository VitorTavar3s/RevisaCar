package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.revisacar.controller.ObterData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarItem extends AppCompatActivity {

    private EditText edit_data_abastecimento,edit_km_veiculo,edit_valor_litro,edit_valor_total;
    private Spinner spinner_combustivel;
    private AppCompatButton btn_alterar;
    private FirebaseFirestore database;
    private String idItem;
    public String tipo_combustivel;
    int valor_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_item);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        idItem = intent.getStringExtra("idItem");
        edit_data_abastecimento = findViewById(R.id.data_abastecimento);
        edit_km_veiculo = findViewById(R.id.km_veiculo);
        edit_valor_litro = findViewById(R.id.valor_litro);
        edit_valor_total = findViewById(R.id.valor_total);
        spinner_combustivel = findViewById(R.id.spinner_combustivel);
        btn_alterar = findViewById(R.id.btn_alterar);
        database = FirebaseFirestore.getInstance();

        edit_data_abastecimento.setText(intent.getStringExtra("data"));
        edit_km_veiculo.setText(intent.getStringExtra("km_veiculo"));
        tipo_combustivel = intent.getStringExtra("tipo_combustivel");

        if (tipo_combustivel != null) {
            switch (tipo_combustivel) {
                case "Gasolina": valor_spinner = 0;
                    break;
                case "Etanol": valor_spinner = 1;
                    break;
                case "Diesel": valor_spinner = 2;
                    break;
            }
        }

        spinner_combustivel.setSelection(valor_spinner);
        edit_valor_litro.setText(intent.getStringExtra("valor_litro"));
        edit_valor_total.setText(intent.getStringExtra("valor_total"));

        edit_data_abastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObterData obterData = new ObterData(edit_data_abastecimento);
                obterData.show(getSupportFragmentManager(),"seletor_data");

            }
        });

        btn_alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data,km_veiculo,valor_litro,valor_total,tipo_combustivel;
                data = edit_data_abastecimento.getText().toString();
                km_veiculo = edit_km_veiculo.getText().toString();
                valor_litro = edit_valor_litro.getText().toString();
                valor_total = edit_valor_total.getText().toString();
                tipo_combustivel = spinner_combustivel.getSelectedItem().toString();

                Map<String,Object> abastecer = new HashMap<>();
                abastecer.put("Data",data);
                abastecer.put("km_veiculo",km_veiculo);
                abastecer.put("valor_litro",valor_litro);
                abastecer.put("valor_total",valor_total);
                abastecer.put("tipo_combustivel",tipo_combustivel);


                database.collection("Abastecimento")
                        .document(idItem)
                        .update(abastecer)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditarItem.this, "Dados Atualizados com Sucesso!",
                                        Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditarItem.this,VisualAbastecer.class);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditarItem.this, "Erro ao atualizar dados!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }
}