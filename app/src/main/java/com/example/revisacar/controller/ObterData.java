package com.example.revisacar.controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.revisacar.R;

import java.util.Calendar;
import java.util.Locale;

public class ObterData extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    private EditText editTextData;

    public ObterData(EditText editTextData) {
        this.editTextData = editTextData;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH);
        int ano = c.get(Calendar.YEAR);

        Locale locale =new Locale("pt","BR");
        Locale.setDefault(locale);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this,ano, mes, dia);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        //datePickerDialog.getDatePicker().setBackgroundColor();
        return datePickerDialog;
    }
    @Override
    public void onDateSet(DatePicker view, int ano, int mes, int dia) {
        String dataSelect = String.format("%02d/%02d/%04d",dia,mes+1,ano);
        editTextData.setText(dataSelect);
    }
    public void setMaxDate(long MaxDate){

    }


}