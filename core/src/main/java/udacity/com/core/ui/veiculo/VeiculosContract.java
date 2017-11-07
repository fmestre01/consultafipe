package udacity.com.core.ui.veiculo;

import java.util.List;

import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.base.RemoteView;

public interface VeiculosContract {

    interface ViewActions {

        void onMarcasRequested();

        void onVeiculosMarcaRequested(Long marcaId, int limit);

        void onVeiculosModeloAnoRequested(Long marcaId, Long veiculoId, int limit);

        void onVeiculoDetalheRequested(Long veiculoId, int limit);
    }

    interface VeiculoView extends RemoteView {

        void showMarcas();

        void showVeiculosMarcaList(List<Veiculo> veiculoList);

        void showVeiculosModeloAnoList(List<Veiculo> veiculoList);

        void showVeiculoDetalhe(Veiculo veiculo);
    }
}
