package widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import marcaslist.MarcasActivity;
import udacity.com.consultafipe.R;
import udacity.com.core.Application;
import udacity.com.core.util.ConstantsUtils;
import util.ConsultaFipeUtils;

public class CollectionWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            try {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.setComponent(new ComponentName(context.getPackageName(), MarcasActivity.class.getName()));
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.consulta_fipe_widget);
                views.setTextViewText(R.id.widget_title, ConstantsUtils.Application.CONSULTA_FIPE + " " + complementoInformacao());
                views.setOnClickPendingIntent(R.id.frame_widget, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetId, views);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(context.getApplicationContext(), ConstantsUtils.ListLog.ERROR, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String complementoInformacao() {
        if (Application.codigoTipoVeiculo != null && Application.codigoTabelaReferencia != null) {
            return " - " + ConsultaFipeUtils.selectTipoVeiculoName(Application.codigoTipoVeiculo) + " - " +
                    Application.codigoTabelaReferencia.getMes();
        }
        return "";
    }
}

