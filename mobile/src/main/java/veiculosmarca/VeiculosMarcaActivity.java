package veiculosmarca;

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
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaContract;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaPresenter;
import veiculosanomodelo.VeiculosModeloAnoActivity;

public class VeiculosMarcaActivity extends AppCompatActivity implements VeiculosMarcaContract.View, VeiculosMarcaContract.OnItemClickListener {

    private static final String EXTRA_ID_MARCA = "idMarca";

    private VeiculosMarcaPresenter veiculosMarcaPresenter;
    private VeiculosMarcaAdapter veiculosMarcaAdapter;

    @BindView(R.id.emptyTextView)
    TextView mEmptyTextView;

    @BindView(R.id.listVeiculosMarca)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    String idMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculos_marca_activity_list);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        veiculosMarcaAdapter = new VeiculosMarcaAdapter(this);
        recyclerView.setAdapter(veiculosMarcaAdapter);

        veiculosMarcaPresenter = new VeiculosMarcaPresenter();
        veiculosMarcaPresenter.attachView(this);

        idMarca = getIntent().getExtras().getString(EXTRA_ID_MARCA);
    }

    @Override
    protected void onStart() {
        super.onStart();
        veiculosMarcaPresenter.onVeiculosMarcaRequested(idMarca);
    }


    @Override
    public void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList) {
        veiculosMarcaAdapter.setValues(veiculoMarcaList);
    }

    @Override
    public void clickItem(VeiculoMarca veiculoMarca) {
        startActivity(VeiculosModeloAnoActivity.newVeiculosModeloAnoActivity(this, idMarca, veiculoMarca.getId()));
    }

    @Override
    public void clickLongItem(VeiculoMarca veiculoMarca) {

    }

    @Override
    public void showProgress() {
        if (veiculosMarcaAdapter.isEmpty()) {
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

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public static Intent newVeiculosMarcaActivity(Context context, String idMarca) {
        Intent intent = new Intent(context, VeiculosMarcaActivity.class);
        intent.putExtra("idMarca", idMarca);
        return intent;
    }
}
