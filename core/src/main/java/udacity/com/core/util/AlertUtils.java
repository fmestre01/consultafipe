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

import udacity.com.core.Application;
import udacity.com.core.R;
import udacity.com.core.model.CombustivelModeloAno;
import udacity.com.core.model.TabelaReferencia;

public class AlertUtils {

    private static String anoModelo;
    private static TabelaReferencia selectedTabelaReferencia;

    public static void alertViewAnoVeiculo(final Context context, String title, String message, int icon, final Intent intent, int layout, final List<CombustivelModeloAno> anos) {
        List<String> label;
        List<String> value;
        String[] stringArr;
        anoModelo = null;

        LayoutInflater factory = LayoutInflater.from(context);

        final View view = factory.inflate(layout, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(true);
        alert.setIcon(icon).setTitle(title).setMessage(message).setView(view).setPositiveButton(context.getResources().getString(R.string.text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        if (anoModelo != null) {
                            intent.putExtra("anoModelo", anoModelo);
                        } else {
                            intent.putExtra("anoModelo", anos.get(0).getId());
                        }
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
            numberPicker.setOnLongPressUpdateInterval(100);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(stringArr.length - 1);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setDisplayedValues(stringArr);
            final List<String> finalValue = value;
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    anoModelo = finalValue.get(i1);
                }
            });
            intent.putExtra("anoModelo", anoModelo);

            alert.setView(numberPicker);
        }
        alert.show();
    }

    public static void alertViewAnoReferencia(final Context context, String title, String message, int icon, int layout, final List<TabelaReferencia> anosReferencia, final Intent intent, boolean cancelable) {
        List<String> label;
        List<String> value;
        String[] stringArr;
        final android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(context);

        LayoutInflater factory = LayoutInflater.from(context);

        final View view = factory.inflate(layout, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setIcon(icon).setTitle(title).setMessage(message).setView(view).setPositiveButton(context.getResources().getString(R.string.text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        if (!Application.codigoTabelaReferencia.equals(anosReferencia.get(numberPicker.getValue()))) {
                            Application.codigoTabelaReferencia = anosReferencia.get(numberPicker.getValue());
                        }

                        context.startActivity(intent);
                    }
                }).setNegativeButton(context.getResources().getString(R.string.text_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });

        alert.setCancelable(true);
        label = new ArrayList<>();
        value = new ArrayList<>();

        if (anosReferencia != null) {
            for (int i = 0; i < anosReferencia.size(); i++) {
                label.add(anosReferencia.get(i).getMes());
                value.add(anosReferencia.get(i).getId());
            }

            stringArr = label.toArray(new String[anosReferencia.size()]);

            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setOnLongPressUpdateInterval(5000);
            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(stringArr.length - 1);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setDisplayedValues(stringArr);

            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                    Application.codigoTabelaReferencia = anosReferencia.get(i1);
                }
            });

            alert.setView(numberPicker);
            alert.show();
        }
    }

    public static void alertViewTipoVeiculo(final Context context, String title, String message, int icon, int layout, final Intent intent) {
        LayoutInflater factory = LayoutInflater.from(context);

        final String selectedTipoVeiculo = null;
        final View view = factory.inflate(layout, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setCancelable(false);
        alert.setIcon(icon).setTitle(title).setMessage(message).setView(view).setPositiveButton(context.getResources().getString(R.string.text_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        intent.putExtra("selectedTipoVeiculo", selectedTipoVeiculo);
                        context.startActivity(intent);
                    }
                }).setNegativeButton(context.getResources().getString(R.string.text_cancelar),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                    }
                });

        android.widget.NumberPicker numberPicker = new android.widget.NumberPicker(context);
        numberPicker.setOnLongPressUpdateInterval(5000);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(2);
        numberPicker.setWrapSelectorWheel(true);
        String[] array = context.getResources().getStringArray(R.array.tipos_veiculos);
        numberPicker.setDisplayedValues(array);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Application.codigoTipoVeiculo = String.valueOf(i1 + 1);
            }
        });
        alert.setView(numberPicker);
        alert.show();
    }
}