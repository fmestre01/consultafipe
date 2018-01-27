package veiculodetalhe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String EXTRA_ID_MODELO = "idModelo";

    private VeiculoDetalhePresenter veiculoDetalhePresenter;

    @BindView(R.id.progress)
    ProgressBar progressBar;

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

    private static String idMarca;
    private static String idModelo;
    private static String anoModelo;
    private static String codigoTipoCombustivel;
    private static String tipoVeiculo;
    private static String tipoConsulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_veiculo_detalhe);

        ButterKnife.bind(this);

        veiculoDetalhePresenter = new VeiculoDetalhePresenter();
        veiculoDetalhePresenter.attachView(this);

        if (getIntent().getExtras() != null) {
            idMarca = getIntent().getExtras().getString("idMarca");
            idModelo = getIntent().getExtras().getString("idModelo");
            anoModelo = getIntent().getExtras().getString("anoModelo");
            tipoConsulta = getIntent().getExtras().getString("tipoConsulta");
            tipoVeiculo = getIntent().getExtras().getString("tipoVeiculo");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        veiculoDetalhePresenter.onVeiculoDetalheRequested(veiculoDetalheJsonObject());
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

    public static Intent newVeiculoDetalheActivity(Context context, String idMarca, String idModelo) {
        Intent intent = new Intent(context, VeiculoDetalheActivity.class);
        intent.putExtra("idMarca", idMarca);
        intent.putExtra("idModelo", idModelo);
        intent.putExtra("tipoVeiculo", "carro");
        intent.putExtra("tipoConsulta", "tradicional");
        intent.putExtra(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, ConstantsUtils.RequestParameters.VALOR_TABELA_REFERENCIA);
        intent.putExtra(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO, ConstantsUtils.RequestParameters.VALOR_TIPO_VEICULO);
        return intent;
    }

    private JSONObject veiculoDetalheJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("codigoMarca", idMarca);
            jsonObject.put("codigoModelo", idModelo);
            jsonObject.put("codigoTipoCombustivel", anoModelo.substring(5));
            jsonObject.put("codigoTipoVeiculo", "1");
            jsonObject.put("anoModelo", anoModelo.substring(0, 4));
            jsonObject.put("modeloCodigoExterno", "");
            jsonObject.put("tipoVeiculo", "carro");
            jsonObject.put("tipoConsulta", "tradicional");
            jsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, ConstantsUtils.RequestParameters.VALOR_TABELA_REFERENCIA);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void showVeiculoDetalhe(Veiculo veiculo) {
        marcaTextView.setText(veiculo.getMarca());
        veiculoTextView.setText(veiculo.getModelo());
        combustivelTextView.setText(veiculo.getCombustivel());
        referenciaTextView.setText(veiculo.getReferencia());
        precoTextView.setText(veiculo.getValor());
    }

    @Override
    public void clickItem(Veiculo veiculo) {

    }

    @Override
    public void clickLongItem(Veiculo veiculo) {

    }
}
