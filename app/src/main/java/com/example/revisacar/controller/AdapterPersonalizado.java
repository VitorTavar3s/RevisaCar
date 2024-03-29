package com.example.revisacar.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.revisacar.R;

import java.util.List;

public class AdapterPersonalizado extends ArrayAdapter<String[]>{

    private List<String[]> itemList;
    private Context context;

    public AdapterPersonalizado(Context context, List<String[]> itemList){
        super(context,0,itemList);
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_abastecer, null);
        }

        String[] item = getItem(position);
        if (item != null) {
            TextView id_abastecimento = view.findViewById(R.id.id_abastecimento);
            TextView data = view.findViewById(R.id.data_abastecimento);
            TextView km_veiculo = view.findViewById(R.id.km_veiculo);
            TextView tipo_combustivel = view.findViewById(R.id.tipo_combustivel);
            TextView valor_litro = view.findViewById(R.id.valor_litro);
            TextView valor_total = view.findViewById(R.id.valor_total);



            id_abastecimento.setText("Abastecimento "+item[0]);
            data.setText(item[1]);
            km_veiculo.setText(item[2]+" Km");
            tipo_combustivel.setText(item[3]);
            valor_litro.setText("R$"+item[4]+"/L");
            valor_total.setText("Total: R$"+item[5]);

            int cor1 = ContextCompat.getColor(getContext(),R.color.cor1);
            int cor2 = ContextCompat.getColor(getContext(),R.color.cor2);
            int rndColor = (position %2 == 0) ? cor1 : cor2;
            view.setBackgroundColor(rndColor);

        }

        return view;
    }
}
