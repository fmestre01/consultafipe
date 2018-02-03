package udacity.com.core.ui.marcas;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.List;

import udacity.com.core.model.AnoReferencia;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.base.RemoteView;

public interface MarcasContract {

    interface Presenter {

        void onMarcasRequestedFastNetworkingLibrary(JSONObject marcaJsonObject);

        void clearData();

        void showAlertDialogPeriodoReferencia(Context context, String title, String message, int icon, int layout, List<AnoReferencia> anosReferencia, Intent intent);

        void showAlertDialogTipoVeiculo(Context context, String title, String message, int icon, int layout, Intent intent);

        List<AnoReferencia> onAnosReferenciaRequested(InputStream in);
    }

    interface View extends RemoteView {

        void showMarcas(List<Marca> marcaList);
    }

    interface OnItemClickListener {

        void clickItem(Marca marca);

        void clickLongItem(Marca marca);
    }

}
