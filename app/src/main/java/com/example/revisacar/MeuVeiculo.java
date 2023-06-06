package com.example.revisacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MeuVeiculo extends AppCompatActivity {

    private ImageButton abastecimento,visualizar_abastecimento,revisao,visualizar_revisao;
    private TextView text_abastecimento,text_visualizar_abastecimento,text_revisao,text_visualizar_revisao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meu_veiculo);
        getSupportActionBar().hide();

        abastecimento = findViewById(R.id.abastecimento);
        text_abastecimento = findViewById(R.id.text_abastecimento);
        visualizar_abastecimento = findViewById(R.id.visualizar_abastecimento);
        text_visualizar_abastecimento = findViewById(R.id.text_visualizar_abastecimento);
        revisao = findViewById(R.id.revisao);
        text_revisao = findViewById(R.id.text_revisao);
        visualizar_revisao = findViewById(R.id.visualizar_revisao);
        text_visualizar_revisao = findViewById(R.id.text_visualizar_revisao);


        abastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this,Abastecer.class);
                startActivity(intent);
            }
        });

        text_abastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this,Abastecer.class);
                startActivity(intent);
            }
        });

        visualizar_abastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this, VisualAbastecer.class);
                startActivity(intent);
            }
        });

        text_visualizar_abastecimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this, VisualAbastecer.class);
                startActivity(intent);
            }
        });

        revisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this,Revisao.class);
                startActivity(intent);
            }
        });

        text_revisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MeuVeiculo.this,Revisao.class);
                startActivity(intent);
            }
        });



    }
}