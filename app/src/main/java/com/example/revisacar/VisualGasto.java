package com.example.revisacar;

import android.app.Activity;
import android.graphics.*;
import android.os.Bundle;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.*;

public class VisualGasto extends AppCompatActivity {

    private XYPlot plot;
    FirebaseFirestore database;
    List<String[]> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_gasto);
        getSupportActionBar().hide();

        database = FirebaseFirestore.getInstance();
        receberDatas();
        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.grafico);

        Number[] values = {1,2,3,4,5,6,7,8,9,10,11,12}; // Substitua isso pelos seus próprios valores

        plot.setDomainStep(StepMode.INCREMENT_BY_VAL,1);

        XYSeries series = new SimpleXYSeries(
                Arrays.asList(values),
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,
                "Gasto Mensal"
        );

        /*BarRenderer renderer = plot.getRenderer(BarRenderer.class); Aumentar a largura da barra
        renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH,10);*/


        plot.setDomainBoundaries(-1, 11.5,BoundaryMode.FIXED);
        plot.setLinesPerDomainLabel(1);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer buffer, FieldPosition fieldPosition) {
                int indice = Math.round(((Number) obj).floatValue());
                String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

                if (indice >= 0 && indice < meses.length) {
                    buffer.append(meses[indice]);
                }
                return buffer;
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
            }
        });



            // Crie um formato para as barras (cor e estilo)
            BarFormatter barFormatter = new BarFormatter(Color.rgb(100, 150, 100), Color.LTGRAY);
            //BarFormatter barFormatter1 = new BarFormatter

            // Adicione a série de dados ao gráfico com o formato especificado
            plot.addSeries(series, barFormatter);



    }

    private void receberDatas(){
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.collection("Abastecimento")
                .whereEqualTo("userId",userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            itemList = new ArrayList<>();
                            int cont = 1;
                            for (QueryDocumentSnapshot document : task.getResult()){
                                String data = document.getString("Data");
                                String valor_total = document.getString("valor_total");


                                String[] dataItem ={data,valor_total};
                                itemList.add(dataItem);
                                cont++;

                                Toast.makeText(VisualGasto.this, "Lido com sucesso", Toast.LENGTH_SHORT).show();
                            }
                        }else Toast.makeText(VisualGasto.this, "Erro na Leitura dos Dados", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}