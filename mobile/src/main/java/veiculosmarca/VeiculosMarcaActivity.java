package veiculosmarca;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.consultafipe.R;
import udacity.com.core.Application;
import udacity.com.core.model.CombustivelModeloAno;
import udacity.com.core.model.Marca;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaContract;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaPresenter;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.TrackUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.SnackbarUtils;
import veiculodetalhe.VeiculoDetalheActivity;

public class VeiculosMarcaActivity extends AppCompatActivity implements VeiculosMarcaContract.View, VeiculosMarcaContract.OnItemClickListener, SearchView.OnQueryTextListener {

    private static final String EXTRA_MARCA = "marca";

    private VeiculosMarcaPresenter veiculosMarcaPresenter;
    private VeiculosMarcaAdapter veiculosMarcaAdapter;

    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    @BindView(R.id.listVeiculosMarca)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private Marca marca;
    private VeiculoMarca veiculoMarcaObject;

    private List<VeiculoMarca> veiculosMarca = new ArrayList<>();

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculos_marca_activity_list);

        TrackUtils.trackEvent(ConstantsUtils.TrackEvent.SCREEN_VEICULOS_MARCA);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            marca = getIntent().getParcelableExtra(EXTRA_MARCA);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        veiculosMarcaAdapter = new VeiculosMarcaAdapter(this, getApplicationContext(), veiculosMarca, marca.getName());
        recyclerView.setAdapter(veiculosMarcaAdapter);

        veiculosMarcaPresenter = new VeiculosMarcaPresenter();

        veiculosMarcaPresenter.attachView(this);
        veiculosMarcaPresenter.onVeiculosMarcaFastNetworkingLibrary(marcaJsonObject());
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList) {
        veiculosMarca = veiculoMarcaList;
        veiculosMarcaAdapter.setValues(veiculoMarcaList);
    }

    @Override
    public void showCombustivelAnoModelo(List<CombustivelModeloAno> combustivelModeloAno) {
        veiculosMarcaPresenter.showAlertDialogAnoVeiculo(
                VeiculosMarcaActivity.this,
                getResources().getString(R.string.text_ano_veiculo),
                getResources().getString(R.string.text_selecione),
                R.mipmap.ic_launcher_round,
                VeiculoDetalheActivity.newVeiculoDetalheActivity(this, marca.getId(),
                        veiculoMarcaObject.getId()),
                R.layout.alert_filtro_veiculo,
                combustivelModeloAno);
    }

    @Override
    public void clickItem(VeiculoMarca veiculoMarca) {
        veiculoMarcaObject = veiculoMarca;
        veiculosMarcaPresenter.loadCombustivelModelosAnos(combustivelModeloAnoJsonObject(veiculoMarca.getId()));
    }

    @Override
    public void clickLongItem(VeiculoMarca veiculoMarca) {
        SnackbarUtils.showSnakbarTipoUm(this.recyclerView, getString(R.string.text_soon));
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
        SnackbarUtils.showSnakbarTipoUm(this.emptyTextView, ConstantsUtils.InfoLog.ERROR);
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public static Intent newVeiculosMarcaActivity(Context context, Marca marca) {
        Intent intent = new Intent(context, VeiculosMarcaActivity.class);
        intent.putExtra("marca", marca);
        return intent;
    }

    private JSONObject marcaJsonObject() {
        JSONObject marcaJsonObject = new JSONObject();
        try {
            marcaJsonObject.put("codigoMarca", marca.getId());
            marcaJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, Application.codigoTabelaReferencia.getId());
            marcaJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO, Application.codigoTipoVeiculo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marcaJsonObject;
    }

    private JSONObject combustivelModeloAnoJsonObject(String codigoModelo) {
        JSONObject combustivelModeloAnoJsonObject = new JSONObject();
        try {
            combustivelModeloAnoJsonObject.put("codigoMarca", marca.getId());
            combustivelModeloAnoJsonObject.put("codigoModelo", codigoModelo);
            combustivelModeloAnoJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, Application.codigoTabelaReferencia.getId());
            combustivelModeloAnoJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO, Application.codigoTipoVeiculo);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return combustivelModeloAnoJsonObject;
    }

    @Override

    protected void onDestroy() {
        super.onDestroy();
        veiculosMarcaPresenter.clearData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        final MenuItem anoReferenciaItem = menu.findItem(R.id.action_ano_referencia);
        anoReferenciaItem.setVisible(false);

        final MenuItem tipoVeiculoItem = menu.findItem(R.id.action_tipo_veiculo);
        tipoVeiculoItem.setVisible(false);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView searchText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHintTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHint(getResources().getString(R.string.text_digite_nome_veiculo));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<VeiculoMarca> filteredModelList = filter(veiculosMarca, newText);
        if (filteredModelList.size() > 0) {
            veiculosMarcaAdapter.setFilter();
            return true;
        } else {
            Toast.makeText(VeiculosMarcaActivity.this, getResources().getString(R.string.text_sem_resultados), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private List<VeiculoMarca> filter(List<VeiculoMarca> veiculosMarca, String query) {

        query = query.toLowerCase();
        String searchString = query;

        final List<VeiculoMarca> filteredModelList = new ArrayList<>();
        for (VeiculoMarca veiculoMarca : veiculosMarca) {
            final String text = veiculoMarca.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(veiculoMarca);
            }
        }
        veiculosMarcaAdapter = new VeiculosMarcaAdapter(this, getApplicationContext(), filteredModelList, marca.getName());
        recyclerView.setLayoutManager(new LinearLayoutManager(VeiculosMarcaActivity.this));
        recyclerView.setAdapter(veiculosMarcaAdapter);
        veiculosMarcaAdapter.notifyDataSetChanged();

        return filteredModelList;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("ano", Application.codigoTabelaReferencia.getMes() + Application.codigoTipoVeiculo);
    }
}
