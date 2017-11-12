package veiculosanomodelo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.consultafipe.R;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.ui.veiculosmodeloano.VeiculosModeloAnoContract;
import udacity.com.core.ui.veiculosmodeloano.VeiculosModeloAnoPresenter;
import udacity.com.core.util.Constants;
import util.UtilSnackbar;
import veiculodetalhe.VeiculoDetalheActivity;

public class VeiculosModeloAnoActivity extends AppCompatActivity implements VeiculosModeloAnoContract.View, VeiculosModeloAnoContract.OnItemClickListener {

    private static final String EXTRA_ID_MARCA = "idMarca";
    private static final String EXTRA_ID_MODELO = "idModeloAno";

    private VeiculosModeloAnoPresenter veiculosModeloAnoPresenter;
    private VeiculosModeloAnoAdapter veiculosModeloAnoAdapter;

    @BindView(R.id.emptyTextView)
    TextView mEmptyTextView;

    @BindView(R.id.listVeiculosModeloAno)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    String idMarca;
    String idModeloAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculos_modelo_ano_activity_list);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        veiculosModeloAnoAdapter = new VeiculosModeloAnoAdapter(this);
        recyclerView.setAdapter(veiculosModeloAnoAdapter);

        veiculosModeloAnoPresenter = new VeiculosModeloAnoPresenter();
        veiculosModeloAnoPresenter.attachView(this);

        idMarca = getIntent().getExtras().getString(EXTRA_ID_MARCA);
        idModeloAno = getIntent().getExtras().getString(EXTRA_ID_MODELO);
    }

    @Override
    protected void onStart() {
        super.onStart();
        veiculosModeloAnoPresenter.onVeiculosModeloAnoRequested(idMarca, idModeloAno);
    }

    @Override
    public void showProgress() {
        if (veiculosModeloAnoAdapter.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
        }
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
        UtilSnackbar.showSnakbarTipoUm(this.mEmptyTextView, Constants.InfoLog.ERROR);
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public static Intent newVeiculosModeloAnoActivity(Context context, String idMarca, String idVeiculo) {
        Intent intent = new Intent(context, VeiculosModeloAnoActivity.class);
        intent.putExtra("idMarca", idMarca);
        intent.putExtra("idModeloAno", idVeiculo);
        return intent;
    }

    @Override
    public void showVeiculosModeloAno(List<VeiculoModeloAno> veiculosModeloAno) {
        veiculosModeloAnoAdapter.setValues(veiculosModeloAno);
    }

    @Override
    public void clickItem(VeiculoModeloAno veiculoModeloAno) {
        startActivity(VeiculoDetalheActivity.newVeiculoDetalheActivity(this, idMarca, idModeloAno, veiculoModeloAno.getId()));
    }

    @Override
    public void clickLongItem(VeiculoModeloAno veiculoModeloAno) {

    }
}
