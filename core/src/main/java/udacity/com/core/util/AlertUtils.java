package udacity.com.core.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AlertUtils {

    public static void alertView(Context context, String message, int icon) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("Selecione o ano do veículo:")
                .setIcon(icon)
                .setMessage(message)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        dialoginterface.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                    }
                }).show();
    }
}
