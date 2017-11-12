package marcaslist;

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
import butterknife.Optional;
import udacity.com.consultafipe.R;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.marcas.MarcasContract;
import udacity.com.core.ui.marcas.MarcasPresenter;
import udacity.com.core.util.Constants;
import util.UtilSnackbar;
import veiculosmarca.VeiculosMarcaActivity;

public class MarcasActivity extends AppCompatActivity implements MarcasContract.View, MarcasContract.OnItemClickListener {

    private MarcasPresenter marcasPresenter;
    private MarcasAdapter marcasAdapter;

    @BindView(R.id.emptyTextView)
    TextView mEmptyTextView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marcas_activity_list);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        marcasAdapter = new MarcasAdapter(this);
        recyclerView.setAdapter(marcasAdapter);

        marcasPresenter = new MarcasPresenter();
        marcasPresenter.attachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        marcasPresenter.onMarcasRequested();
    }

    @Override
    public void showMarcas(List<Marca> marcaList) {
        marcasAdapter.setValues(marcaList);
    }

    @Override
    public void clickItem(Marca marca) {
        startActivity(VeiculosMarcaActivity.newVeiculosMarcaActivity(this, marca.getId()));
    }

    @Override
    public void clickLongItem(Marca marca) {

    }

    @Override
    public void showProgress() {
        if (marcasAdapter.isEmpty()) {
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
}
