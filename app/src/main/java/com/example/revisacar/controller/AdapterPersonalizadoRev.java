package com.example.revisacar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.revisacar.R;

import java.util.List;

public class AdapterPersonalizadoRev extends ArrayAdapter<String[]> {

    private List<String[]> itemList;
    private Context context;

    public AdapterPersonalizadoRev(Context context, List<String[]> itemList){
        super(context,0,itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_revisao, null);
        }

        String[] item = getItem(position);
        if (item != null) {
            TextView id_revisao = view.findViewById(R.id.id_revisao);
            TextView km_veiculo = view.findViewById(R.id.km_veiculo);
            TextView data = view.findViewById(R.id.data_revisao);
            TextView descricao = view.findViewById(R.id.descricao);
            TextView valor_revisao = view.findViewById(R.id.valor_revisao);




            id_revisao.setText("Revisão "+item[0]);
            km_veiculo.setText(item[1]+" Km");
            data.setText(item[2]);
            descricao.setText("Descrição:\n"+item[3]);
            valor_revisao.setText("Valor: R$"+item[4]);


            int cor1 = ContextCompat.getColor(getContext(),R.color.cor1);
            int cor2 = ContextCompat.getColor(getContext(),R.color.cor2);
            int rndColor = (position %2 == 0) ? cor1 : cor2;
            view.setBackgroundColor(rndColor);

        }

        return view;
    }


}
