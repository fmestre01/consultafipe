package udacity.com.core.ui.veiculodetalhe;

import org.json.JSONObject;

import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculoDetalheContract {

    interface Presenter {

        void onVeiculoDetalheRequested(JSONObject veiculoDetalhejsonObject);
    }

    interface View extends RemoteView {

        void showVeiculoDetalhe(Veiculo veiculo);
    }

    interface OnItemClickListener {

        void clickItem(Veiculo veiculo);

        void clickLongItem(Veiculo veiculo);
    }

}
