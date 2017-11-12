package udacity.com.core.ui.veiculosmarca;

import java.util.List;

import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculosMarcaContract {

    interface Presenter {

        void onVeiculosMarcaRequested(String idMarca);
    }

    interface View extends RemoteView {

        void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList);
    }

    interface OnItemClickListener {

        void clickItem(VeiculoMarca veiculoMarca);

        void clickLongItem(VeiculoMarca veiculoMarca);
    }

}
