package udacity.com.core.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.List;

import udacity.com.core.R;
import udacity.com.core.model.AnoReferencia;
import udacity.com.core.model.CombustivelModeloAno;

public class AlertUtils {

    private static AnoReferencia selectedAnoReferencia = null;
    private static String anoSelected;

    public static void alertViewAnoVeiculo(final Context context, String title, String message, int icon, final Intent intent, int layout, List<CombustivelModeloAno> anos) {
        List<String> label;
        final List<String> value;
        String[] stringArr;

        LayoutInflater factory = LayoutInflater.from(context);

        final View view = factory.inflate(layout, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(icon).setTitle(title).setMessage(message).setView(view).setPositiveButton(context.getResources().getString(R.string.text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        context.startActivity(intent);
                    }
                }).setNegativeButton(context.getResources().getString(R.string.text_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });

        label = new ArrayList<>();
        value = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            label.add(anos.get(i).getName());
            value.add(anos.get(i).getId());
        }

        stringArr = label.toArray(new String[anos.size()]);
        if (stringArr.length > 0) {
            android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(context);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(stringArr.length - 1);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setDisplayedValues(stringArr);
            final List<String> finalValue = value;
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    anoSelected = finalValue.get(i);
                }
            });
            if (anoSelected == null) {
                anoSelected = finalValue.get(0);
            }
            intent.putExtra("anoModelo", anoSelected);
            alert.setView(numberPicker);
        }
        alert.show();
    }

    public static void alertViewAnoReferencia(final Context context, String title, String message, int icon, int layout, final List<AnoReferencia> anosReferencia, final Intent intent) {
        List<String> label;
        List<String> value;
        String[] stringArr;

        LayoutInflater factory = LayoutInflater.from(context);

        final View view = factory.inflate(layout, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setIcon(icon).setTitle(title).setMessage(message).setView(view).setPositiveButton(context.getResources().getString(R.string.text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        intent.putExtra("selectedAnoReferencia", selectedAnoReferencia);
                        context.startActivity(intent);
                    }
                }).setNegativeButton(context.getResources().getString(R.string.text_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });
        label = new ArrayList<>();
        value = new ArrayList<>();

        for (int i = 0; i < anosReferencia.size(); i++) {
            label.add(anosReferencia.get(i).getMes());
            value.add(anosReferencia.get(i).getId());
        }

        stringArr = label.toArray(new String[anosReferencia.size()]);
        android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(context);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(stringArr.length - 1);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDisplayedValues(stringArr);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                selectedAnoReferencia = anosReferencia.get(i);
            }
        });
        alert.setView(numberPicker);
        alert.show();
    }
}
