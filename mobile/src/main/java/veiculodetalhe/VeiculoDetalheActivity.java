package veiculodetalhe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.com.consultafipe.R;
import udacity.com.core.Application;
import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.veiculodetalhe.VeiculoDetalheContract;
import udacity.com.core.ui.veiculodetalhe.VeiculoDetalhePresenter;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.ConsultaFipeUtils;
import udacity.com.core.util.TrackUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.SnackbarUtils;

public class VeiculoDetalheActivity extends AppCompatActivity implements VeiculoDetalheContract.View, VeiculoDetalheContract.OnItemClickListener {

    private VeiculoDetalhePresenter veiculoDetalhePresenter;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.marca)
    TextView marcaTextView;

    @BindView(R.id.veiculo)
    TextView veiculoTextView;

    @BindView(R.id.tipoVeiculo)
    TextView tipoVeiculoTextView;

    @BindView(R.id.combustivel)
    TextView combustivelTextView;

    @BindView(R.id.anoModelo)
    TextView anoModeloTextView;

    @BindView(R.id.preco)
    TextView precoTextView;

    @BindView(R.id.labelPreco)
    TextView labelPrecoTextView;

    @BindView(R.id.mesReferencia)
    TextView mesReferenciaTextView;

    @BindView(R.id.codigoFipe)
    TextView codigoFipeTextView;

    @BindView(R.id.dataConsulta)
    TextView dataConsultaTextView;

    @BindView(R.id.btnCompartilhar)
    Button btnCompartilhar;

    @BindView(R.id.btnFavorito)
    Button btnFavorito;

    private static String idMarca;
    private static String idModelo;
    private static String anoModelo;
    private static String codigoTipoCombustivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_veiculo_detalhe);

        TrackUtils.trackEvent(ConstantsUtils.TrackEvent.SCREEN_VEICULOS_DETALHE);

        ButterKnife.bind(this);

        veiculoDetalhePresenter = new VeiculoDetalhePresenter();
        veiculoDetalhePresenter.attachView(this);

        if (getIntent().getExtras() != null) {
            idMarca = getIntent().getExtras().getString("idMarca");
            idModelo = getIntent().getExtras().getString("idModelo");
            anoModelo = getIntent().getExtras().getString("anoModelo");
            String tipoConsulta = getIntent().getExtras().getString("tipoConsulta");
            String tipoVeiculo = getIntent().getExtras().getString("tipoVeiculo");
        }

        InterstitialAd mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.key_admob));
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
        SnackbarUtils.showSnakbarTipoUm(this.marcaTextView, ConstantsUtils.InfoLog.ERROR);
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
        intent.putExtra(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, Application.codigoTabelaReferencia);
        intent.putExtra(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO, Application.codigoTipoVeiculo);
        return intent;
    }

    private JSONObject veiculoDetalheJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("codigoMarca", idMarca);
            jsonObject.put("codigoModelo", idModelo);

            if (ConsultaFipeUtils.isVeiculoNovo(anoModelo)) {
                jsonObject.put("codigoTipoCombustivel", anoModelo.substring(6, 7));
                jsonObject.put("anoModelo", anoModelo.substring(0, 4) + "0");
            } else {
                jsonObject.put("codigoTipoCombustivel", anoModelo.substring(5));
                jsonObject.put("anoModelo", anoModelo.substring(0, 4));
            }

            jsonObject.put("modeloCodigoExterno", "");
            jsonObject.put("tipoVeiculo", "carro");
            jsonObject.put("tipoConsulta", "tradicional");
            jsonObject.put("codigoTipoVeiculo", Application.codigoTipoVeiculo);
            jsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, Application.codigoTabelaReferencia.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void showVeiculoDetalhe(Veiculo veiculo) {
        labelPrecoTextView.setVisibility(View.VISIBLE);
        btnCompartilhar.setVisibility(View.VISIBLE);
        btnFavorito.setVisibility(View.VISIBLE);

        marcaTextView.setText(veiculo.getMarca());
        veiculoTextView.setText(veiculo.getModelo());
        combustivelTextView.setText(veiculo.getCombustivel());
        anoModeloTextView.setText(veiculo.getAnoModelo());
        precoTextView.setText(veiculo.getValor());
        tipoVeiculoTextView.setText(util.ConsultaFipeUtils.selectTipoVeiculoName(Application.codigoTipoVeiculo));
        mesReferenciaTextView.setText(getResources().getString(R.string.text_mes_referencia) +
                " " +
                Application.codigoTabelaReferencia.getMes());
        dataConsultaTextView.setText(veiculo.getDataConsulta());
        codigoFipeTextView.setText(getResources().getString(R.string.text_codigo_fipe) +
                " " + veiculo.getCodigoFipe());
    }

    @Override
    public void clickItem(Veiculo veiculo) {

    }

    @Override
    public void clickLongItem(Veiculo veiculo) {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btnCompartilhar)
    void clickShare() {
        SnackbarUtils.showSnakbarTipoUm(this.marcaTextView, getString(R.string.text_soon));
    }

    @OnClick(R.id.btnFavorito)
    void addFavorite() {
        SnackbarUtils.showSnakbarTipoUm(this.marcaTextView, getString(R.string.text_soon));
    }
}
