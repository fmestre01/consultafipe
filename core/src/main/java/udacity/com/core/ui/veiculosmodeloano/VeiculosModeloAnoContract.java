package udacity.com.core.ui.veiculosmodeloano;

import java.util.List;

import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculosModeloAnoContract {

    interface Presenter {

        void onVeiculosModeloAnoRequested(String idMarca, String idModeloAno);
    }

    interface View extends RemoteView {

        void showVeiculosModeloAno(List<VeiculoModeloAno> veiculosModeloAno);
    }

    interface OnItemClickListener {

        void clickItem(VeiculoModeloAno veiculoModeloAno);

        void clickLongItem(VeiculoModeloAno veiculoModeloAno);
    }

}
