package marcaslist;

import android.app.SearchManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.consultafipe.R;
import udacity.com.core.Application;
import udacity.com.core.model.Marca;
import udacity.com.core.model.TabelaReferencia;
import udacity.com.core.ui.marcas.MarcasContract;
import udacity.com.core.ui.marcas.MarcasPresenter;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.TrackUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import util.ConsultaFipeUtils;
import util.DeviceUtils;
import util.SnackbarUtils;
import util.SpacesItemDecorationUtils;
import veiculosmarca.VeiculosMarcaActivity;
import widget.CollectionWidget;

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

    private List<Marca> marcas = new ArrayList<>();

    private Bundle extras;
    private boolean invalidateMenuOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.marcas_activity_list);

        createNewBlankAdapter();

        if (ConsultaFipeUtils.isNetworkConnectionOn(this)) {
            AndroidNetworking.forceCancelAll();

            FirebaseDatabase firebaseInstance = FirebaseDatabase.getInstance();
            DatabaseReference firebaseDatabase = firebaseInstance.getReference(ConstantsUtils.Firebase.USUARIOS_FIREBASE);

            DeviceUtils.insertNewUsuarioFirebase(firebaseDatabase);

            TrackUtils.trackEvent(ConstantsUtils.TrackEvent.SCREEN_MARCA);

            ButterKnife.bind(this);

            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_cardview);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.addItemDecoration(new SpacesItemDecorationUtils(spacingInPixels));

            marcasPresenter = new MarcasPresenter();
            marcasPresenter.attachView(this);

            swipeRefreshLayout.setOnRefreshListener(
                    new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            if (marcas != null && marcas.size() > 0 && ConsultaFipeUtils.isNetworkConnectionOn(getApplicationContext())) {
                                marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject());
                            }
                        }
                    });

            if (Application.tabelasReferencias == null) {
                marcasPresenter.onTabelaReferenciaRequestedFastNetworkingLibrary();
            } else {
                marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject());
            }

            recyclerView.setAdapter(marcasAdapter);

            extras = getIntent().getExtras();
            refreshWidgetAutomatically();
        } else {
            invalidateMenuOptions = true;
            Toast.makeText(this, getResources().getString(R.string.text_erro), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void showMarcas(List<Marca> marcaList) {
        progressBar.setVisibility(View.GONE);
        layoutTentarDeNovo.setVisibility(View.GONE);
        marcas = marcaList;
        marcasAdapter.setValues(marcaList);

        if (Application.codigoTipoVeiculo != null && Application.codigoTabelaReferencia != null) {
            marcasAdapter = new MarcasAdapter(
                    this,
                    this,
                    marcas,
                    ConsultaFipeUtils.selectTipoVeiculoName(Application.codigoTipoVeiculo),
                    getResources().getString(R.string.text_referencia) + " " +
                            Application.codigoTabelaReferencia.getMes());
        }
        recyclerView.setAdapter(marcasAdapter);
    }

    @Override
    public void showTabelaReferencia(List<TabelaReferencia> tabelaReferenciaList) {
        Application.tabelasReferencias = tabelaReferenciaList;

        if (Application.codigoTabelaReferencia == null) {
            Application.codigoTipoVeiculo = ConstantsUtils.TipoVeiculo.CODIGO_CARROS_UTILITARIOS_PEQUENOS;
            Application.codigoTabelaReferencia = tabelaReferenciaList.get(0);
            refreshWidgetAutomatically();
            marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        marcasAdapter.setCanStart(true);
    }

    @Override
    public void clickItem(Marca marca) {
        startActivity(VeiculosMarcaActivity.newVeiculosMarcaActivity(this, marca));
    }

    @Override
    public void clickLongItem(Marca marca) {
        SnackbarUtils.showSnakbarTipoUm(this.recyclerView, ConstantsUtils.InfoLog.SOON);
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
        invalidateMenuOptions = true;
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(String errorMessage) {
        invalidateMenuOptions = true;
        //SnackbarUtils.showSnakbarTipoUm(this.layoutTentarDeNovo, ConstantsUtils.InfoLog.ERROR);
        swipeRefreshLayout.setRefreshing(false);
        tentarDeNovo();
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public void tentarDeNovo() {
        layoutTentarDeNovo.setVisibility(View.VISIBLE);
        marcasPresenter.onMarcasRequestedFastNetworkingLibrary(marcaJsonObject());
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!invalidateMenuOptions) {
            final List<Marca> filteredModelList = filter(marcas, newText);
            if (filteredModelList.size() > 0) {
                marcasAdapter.setFilter();
                return true;
            } else {
                Toast.makeText(MarcasActivity.this, getResources().getString(R.string.text_sem_resultados), Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return false;
    }

    private List<Marca> filter(List<Marca> marcas, String query) {

        query = query.toLowerCase();
        String searchString = query;

        final List<Marca> filteredModelList = new ArrayList<>();
        for (Marca marca : marcas) {
            final String text = marca.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(marca);
            }
        }
        marcasAdapter = new MarcasAdapter(
                this,
                this,
                filteredModelList,
                ConsultaFipeUtils.selectTipoVeiculoName(Application.codigoTipoVeiculo),
                Application.codigoTabelaReferencia.getMes());
        recyclerView.setLayoutManager(new LinearLayoutManager(MarcasActivity.this));
        recyclerView.setAdapter(marcasAdapter);
        marcasAdapter.notifyDataSetChanged();

        return filteredModelList;
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (!invalidateMenuOptions) {
            getMenuInflater().inflate(R.menu.list_menu, menu);

            MenuItem searchitem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            TextView searchText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

            searchText.setTextColor(ContextCompat.getColor(this, R.color.colorSearchView));
            searchText.setHintTextColor(ContextCompat.getColor(this, R.color.colorSearchView));
            searchText.setHint(getResources().getString(R.string.text_digite_nome_marca));
            searchView.setOnQueryTextListener(this);

            final MenuItem anoReferenciaItem = menu.findItem(R.id.action_ano_referencia);

            if (extras != null) {
                if (extras.getParcelable("selectedAnoReferencia") != null) {
                    Application.codigoTabelaReferencia = extras.getParcelable("selectedAnoReferencia");
                }
            }

            anoReferenciaItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    showAlertSelectAnoReferencia();
                    return false;
                }
            });

            final MenuItem tipoVeiculoItem = menu.findItem(R.id.action_tipo_veiculo);

            tipoVeiculoItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    marcasPresenter.showAlertDialogTipoVeiculo(
                            MarcasActivity.this,
                            getResources().getString(R.string.text_tipo_veiculo),
                            getResources().getString(R.string.text_selecione),
                            R.mipmap.consulta_fipe_logo,
                            R.layout.alert_view_generic,
                            MarcasActivity.newMarcasActivity(getApplicationContext()));
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    private static Intent newMarcasActivity(Context context) {
        return new Intent(context, MarcasActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    private JSONObject marcaJsonObject() {
        JSONObject marcaObject = new JSONObject();
        try {
            marcaObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA,
                    Application.codigoTabelaReferencia.getId());
            marcaObject.put(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO,
                    Application.codigoTipoVeiculo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marcaObject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (marcasPresenter != null) {
            marcasPresenter.clearData();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void showAlertSelectAnoReferencia() {
        if (!invalidateMenuOptions) {
            marcasPresenter.showAlertDialogPeriodoReferencia(
                    MarcasActivity.this,
                    getResources().getString(R.string.text_periodo_referencia),
                    getResources().getString(R.string.text_selecione),
                    R.mipmap.consulta_fipe_logo,
                    R.layout.alert_view_generic,
                    Application.tabelasReferencias,
                    MarcasActivity.newMarcasActivity(getApplicationContext()),
                    Application.tabelasReferencias != null);
        }
    }

    private void refreshWidgetAutomatically() {
        Intent intent = new Intent(MarcasActivity.this, CollectionWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), CollectionWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private void createNewBlankAdapter() {
        marcasAdapter = new MarcasAdapter(this,
                this,
                new ArrayList<Marca>());
    }
}
