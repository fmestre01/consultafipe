package udacity.com.core.ui.veiculodetalhe;

import udacity.com.core.model.Veiculo;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculoDetalheContract {

    interface Presenter {

        void onVeiculoDetalheRequested(String idMarca, String idModeloAno, String idVeiculo);
    }

    interface View extends RemoteView {

        void showVeiculoDetalhe(Veiculo veiculo);
    }

    interface OnItemClickListener {

        void clickItem(Veiculo veiculo);

        void clickLongItem(Veiculo veiculo);
    }

}
