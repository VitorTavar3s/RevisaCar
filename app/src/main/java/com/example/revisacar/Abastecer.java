package com.example.revisacar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Abastecer extends AppCompatActivity {

    private EditText edit_data_abastecimento,edit_km_veiculo,edit_valor_litro,edit_valor_total;
    private Spinner spinner_combustivel;
    private AppCompatButton btn_incluir;
    private FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abastecer);
        getSupportActionBar().hide();

        edit_data_abastecimento = findViewById(R.id.data_abastecimento);
        edit_km_veiculo = findViewById(R.id.km_veiculo);
        edit_valor_litro = findViewById(R.id.valor_litro);
        edit_valor_total = findViewById(R.id.valor_total);
        spinner_combustivel = findViewById(R.id.spinner_combustivel);
        btn_incluir = findViewById(R.id.btn_incluir);
        database = FirebaseFirestore.getInstance();

        SimpleMaskFormatter smf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(edit_data_abastecimento,smf);
        edit_data_abastecimento.addTextChangedListener(mtw);

        SimpleMaskFormatter smf2 = new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtw2 = new MaskTextWatcher(edit_valor_litro,smf2);
        edit_valor_litro.addTextChangedListener(mtw2);



        btn_incluir.setOnClickListener(new View.OnClickListener() {
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
                        .add(abastecer)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Abastecer.this, "Dados Incluídos com Sucesso",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });






    }
}