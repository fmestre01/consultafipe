package udacity.com.core.ui.marcas;

import org.json.JSONObject;

import java.util.List;

import udacity.com.core.model.Marca;
import udacity.com.core.ui.base.RemoteView;

public interface MarcasContract {

    interface Presenter {

        void onMarcasRequested();

        void onMarcasRequestedFastNetworkingLibrary(JSONObject marcaJsonObject);

        void clearData();
    }

    interface View extends RemoteView {

        void showMarcas(List<Marca> marcaList);
    }

    interface OnItemClickListener {

        void clickItem(Marca marca);

        void clickLongItem(Marca marca);
    }

}
