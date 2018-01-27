package udacity.com.core.ui.veiculosmarca;

import android.content.Context;
import android.content.Intent;

import org.json.JSONObject;

import java.util.List;

import udacity.com.core.model.CombustivelModeloAno;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculosMarcaContract {

    interface Presenter {
        void onVeiculosMarcaFastNetworkingLibrary(JSONObject veiculoMarcaJsonObject);

        void clearData();

        void showAlertDialogAnoVeiculo(Context context, String title, String message, int icon, final Intent intent, int layout, List<CombustivelModeloAno> anos);

        void loadCombustivelModelosAnos(JSONObject veiculosModeloJsonObject);
    }

    interface View extends RemoteView {

        void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList);

        void showCombustivelAnoMocelo(List<CombustivelModeloAno> combustivelModeloAno);
    }

    interface OnItemClickListener {

        void clickItem(VeiculoMarca veiculoMarca);

        void clickLongItem(VeiculoMarca veiculoMarca);
    }

}
