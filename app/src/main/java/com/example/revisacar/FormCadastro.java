package com.example.revisacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormCadastro extends AppCompatActivity {

    private EditText nome_cadastro,email_cadastro,senha_cadastro,confsenha_cadastro;
    private AppCompatButton btn_cadastrar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_cadastro);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        nome_cadastro = findViewById(R.id.text_nome_cadastro);
        email_cadastro = findViewById(R.id.text_email_cadastro);
        senha_cadastro = findViewById(R.id.text_senha_cadastro);
        confsenha_cadastro = findViewById(R.id.text_confsenha_cadastro);
        btn_cadastrar = findViewById(R.id.btn_cadastro);
        Map<String,Object> usuario = new HashMap<>();



        btn_cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome,email,senha,confsenha;
                nome = nome_cadastro.getText().toString();
                email = email_cadastro.getText().toString();
                senha = senha_cadastro.getText().toString();
                confsenha = confsenha_cadastro.getText().toString();

                if(TextUtils.isEmpty(nome)){
                    Toast.makeText(FormCadastro.this, "Digite um Nome", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(FormCadastro.this, "Digite um Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(senha)){
                    Toast.makeText(FormCadastro.this, "Digite uma Senha", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(confsenha)){
                    Toast.makeText(FormCadastro.this, "Digite uma Confirmação de Senha", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                if (senha.equals(confsenha)) {
                    
                    mAuth.createUserWithEmailAndPassword(email, senha)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(FormCadastro.this, "Conta Criada com Sucesso.",
                                                Toast.LENGTH_SHORT).show();
                                        usuario.put("Nome",nome);
                                        usuario.put("Email",email);
                                        database.collection("usuários")
                                                .add(usuario)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
                                                        Toast.makeText(FormCadastro.this,"Adicionado ao banco de dados",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(FormCadastro.this,"Banco de dados Falhou",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(FormCadastro.this, "Autenticação Falhou.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }else {
                    Toast.makeText(FormCadastro.this, "A senha deve ser igual nos dois campos!", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



}