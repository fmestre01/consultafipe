package udacity.com.core.ui.veiculosmarca;

import org.json.JSONObject;

import java.util.List;

import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculosMarcaContract {

    interface Presenter {

        void onVeiculosMarcaRequested(String idMarca);

        void onVeiculosMarcaFastNetworkingLibrary(JSONObject veiculoMarcaJsonObject);

        void clearData();
    }

    interface View extends RemoteView {

        void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList);
    }

    interface OnItemClickListener {

        void clickItem(VeiculoMarca veiculoMarca);

        void clickLongItem(VeiculoMarca veiculoMarca);
    }

}
