package veiculodetalhe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.consultafipe.R;
import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.veiculodetalhe.VeiculoDetalheContract;
import udacity.com.core.ui.veiculodetalhe.VeiculoDetalhePresenter;
import udacity.com.core.util.ConstantsUtils;
import util.UtilSnackbar;

public class VeiculoDetalheActivity extends AppCompatActivity implements VeiculoDetalheContract.View, VeiculoDetalheContract.OnItemClickListener {

    private static final String EXTRA_ID_MARCA = "idMarca";
    private static final String EXTRA_ID_MODELO_ANO = "idModeloAno";
    private static final String EXTRA_ID_VEICULO = "idVeiculo";

    private VeiculoDetalhePresenter veiculoDetalhePresenter;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    String idMarca;
    String idModeloAno;
    String idVeiculo;

    @BindView(R.id.marca)
    TextView marcaTextView;

    @BindView(R.id.veiculo)
    TextView veiculoTextView;

    @BindView(R.id.combustivel)
    TextView combustivelTextView;

    @BindView(R.id.referencia)
    TextView referenciaTextView;

    @BindView(R.id.preco)
    TextView precoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_veiculo_detalhe);

        ButterKnife.bind(this);

        veiculoDetalhePresenter = new VeiculoDetalhePresenter();
        veiculoDetalhePresenter.attachView(this);

        idMarca = getIntent().getExtras().getString(EXTRA_ID_MARCA);
        idModeloAno = getIntent().getExtras().getString(EXTRA_ID_MODELO_ANO);
        idVeiculo = getIntent().getExtras().getString(EXTRA_ID_VEICULO);
    }

    @Override
    protected void onStart() {
        super.onStart();
        veiculoDetalhePresenter.onVeiculoDetalheRequested(idMarca, idModeloAno, idVeiculo);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUnauthorizedError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {
        UtilSnackbar.showSnakbarTipoUm(this.marcaTextView, ConstantsUtils.InfoLog.ERROR);
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public static Intent newVeiculoDetalheActivity(Context context, String idMarca, String idModeloAno, String idVeiculo) {
        Intent intent = new Intent(context, VeiculoDetalheActivity.class);
        intent.putExtra("idMarca", idMarca);
        intent.putExtra("idModeloAno", idModeloAno);
        intent.putExtra("idVeiculo", idVeiculo);
        return intent;
    }

    @Override
    public void showVeiculoDetalhe(Veiculo veiculo) {
        marcaTextView.setText(veiculo.getMarca());
        veiculoTextView.setText(veiculo.getVeiculo());
        combustivelTextView.setText(veiculo.getCombustivel());
        referenciaTextView.setText(veiculo.getReferencia());
        precoTextView.setText(veiculo.getPreco());
    }

    @Override
    public void clickItem(Veiculo veiculo) {

    }

    @Override
    public void clickLongItem(Veiculo veiculo) {

    }
}
