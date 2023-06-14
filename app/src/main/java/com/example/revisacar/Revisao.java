package com.example.revisacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Revisao extends AppCompatActivity {

    private EditText edit_data_revisao,edit_km_veiculo,edit_valor_revisao,edit_descricao_revisao;
    private AppCompatButton btn_revisao;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revisao);
        getSupportActionBar().hide();

        edit_data_revisao = findViewById(R.id.data_revisao);
        edit_km_veiculo = findViewById(R.id.km_veiculo);
        edit_valor_revisao = findViewById(R.id.valor_revisao);
        edit_descricao_revisao = findViewById(R.id.descricao_revisao);
        btn_revisao = findViewById(R.id.btn_revisao);
        database = FirebaseFirestore.getInstance();

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_data_revisao,smf);
        edit_data_revisao.addTextChangedListener(mtw);


        btn_revisao.setOnClickListener(new View.OnClickListener() {
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

                database.collection("Revisão")
                        .add(revisao)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Revisao.this,"Dados Incluídos com Sucesso",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



    }
}