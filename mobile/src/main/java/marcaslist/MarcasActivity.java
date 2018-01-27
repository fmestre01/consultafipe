package marcaslist;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.com.consultafipe.R;
import udacity.com.core.model.AnoReferencia;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.marcas.MarcasContract;
import udacity.com.core.ui.marcas.MarcasPresenter;
import udacity.com.core.util.ConstantsUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.SpacesItemDecoration;
import util.UtilSnackbar;
import veiculosmarca.VeiculosMarcaActivity;

public class MarcasActivity extends AppCompatActivity implements MarcasContract.View, MarcasContract.OnItemClickListener, SearchView.OnQueryTextListener {

    private MarcasPresenter marcasPresenter;
    private MarcasAdapter marcasAdapter;

    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.layoutEmptyData)
    LinearLayout layoutTentarDeNovo;

    String searchString = "";
    List<Marca> marcasPesquisa = new ArrayList<>();

    List<AnoReferencia> anosReferencia;
    AnoReferencia anoReferenciaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marcas_activity_list);

        ButterKnife.bind(this);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_cardview);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        marcasAdapter = new MarcasAdapter(this, getApplicationContext(), marcasPesquisa);
        recyclerView.setAdapter(marcasAdapter);

        marcasPresenter = new MarcasPresenter();
        marcasPresenter.attachView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            anoReferenciaSelected = extras.getParcelable("selectedAnoReferencia");
        }

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject(anoReferenciaSelected.getId()));
                    }
                }
        );

        populaTabelaAnosReferencia();

        if (anoReferenciaSelected == null) {
            anoReferenciaSelected = anosReferencia.get(0);
        }
        marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject(anoReferenciaSelected.getId()));
        UtilSnackbar.showSnakbarTipoUm(this.emptyTextView, "Ano Referência: " + anoReferenciaSelected.getMes());
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void showMarcas(List<Marca> marcaList) {
        marcasPesquisa = marcaList;
        marcasAdapter.setValues(marcaList);
    }

    @Override
    public void clickItem(Marca marca) {
        startActivity(VeiculosMarcaActivity.newVeiculosMarcaActivity(this, marca));
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
        layoutTentarDeNovo.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showUnauthorizedError() {
        swipeRefreshLayout.setRefreshing(false);
        layoutTentarDeNovo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showEmpty() {
        swipeRefreshLayout.setRefreshing(false);
        layoutTentarDeNovo.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String errorMessage) {
        UtilSnackbar.showSnakbarTipoUm(this.emptyTextView, ConstantsUtils.InfoLog.ERROR);
        layoutTentarDeNovo.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @OnClick(R.id.layoutEmptyData)
    public void tentarDeNovo() {
        marcasPresenter.onMarcasRequested();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Marca> filteredModelList = filter(marcasPesquisa, newText);
        if (filteredModelList.size() > 0) {
            marcasAdapter.setFilter(filteredModelList);
            return true;
        } else {
            Toast.makeText(MarcasActivity.this, getResources().getString(R.string.text_sem_resultados), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private List<Marca> filter(List<Marca> marcas, String query) {

        query = query.toLowerCase();
        this.searchString = query;

        final List<Marca> filteredModelList = new ArrayList<>();
        for (Marca marca : marcas) {
            final String text = marca.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(marca);
            }
        }
        marcasAdapter = new MarcasAdapter(this, getApplicationContext(), filteredModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MarcasActivity.this));
        recyclerView.setAdapter(marcasAdapter);
        marcasAdapter.notifyDataSetChanged();

        return filteredModelList;
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(ContextCompat.getColor(this, R.color.colorSearchView));
        searchText.setHintTextColor(ContextCompat.getColor(this, R.color.colorSearchView));
        searchText.setHint(getResources().getString(R.string.text_digite_nome_marca));
        searchView.setOnQueryTextListener(this);

        final MenuItem anoReferenciaItem = menu.findItem(R.id.action_ano_referencia);
        anoReferenciaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                marcasPresenter.showAlertDialogPeriodoReferencia(
                        MarcasActivity.this,
                        getResources().getString(R.string.text_periodo_referencia),
                        getResources().getString(R.string.text_selecione),
                        R.mipmap.ic_launcher_round,
                        R.layout.alert_ano_referencia_veiculo,
                        anosReferencia,
                        MarcasActivity.newMarcasActivity(getApplicationContext(), anoReferenciaSelected.getId()));
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public static Intent newMarcasActivity(Context context, String codigoAnoReferencia) {
        Intent intent = new Intent(context, MarcasActivity.class);
        intent.putExtra("codigoAnoReferencia", codigoAnoReferencia);
        return intent;
    }

    private JSONObject marcaJsonObject(String valorTabelaReferencia) {
        JSONObject marcaObject = new JSONObject();
        try {
            marcaObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA, valorTabelaReferencia);
            marcaObject.put(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO, ConstantsUtils.RequestParameters.VALOR_TIPO_VEICULO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marcaObject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        marcasPresenter.clearData();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void populaTabelaAnosReferencia() {
        try {
            InputStream assetsAnosReferencia = getAssets().open("anosReferencia.json");
            anosReferencia = marcasPresenter.onAnosReferenciaRequested(assetsAnosReferencia);

            //if (SharedPrefsUtils.getBooleanPreference(this, ConstantsUtils.Data.SAVE_DATA_ANO_REFERENCIA, false)) {
            /*InputStream assetsAnosReferencia = getAssets().open("anosReferencia.json");
            anosReferencia = marcasPresenter.onAnosReferenciaRequested(assetsAnosReferencia);
            int size = anosReferencia.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    AnoReferencia anoReferencia = new AnoReferencia();
                    anoReferencia.setId(anosReferencia.get(i).getId());
                    anoReferencia.setMes(anosReferencia.get(i).getMes());
                    BaseApplication.db.anoReferenciaDao().insertAnosReferencia(anoReferencia);
                }
                SharedPrefsUtils.setBooleanPreference(this, ConstantsUtils.Data.SAVE_DATA_ANO_REFERENCIA, true);
            }*/
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
