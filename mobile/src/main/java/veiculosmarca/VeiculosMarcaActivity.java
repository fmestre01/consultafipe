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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import udacity.com.consultafipe.R;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaContract;
import udacity.com.core.ui.veiculosmarca.VeiculosMarcaPresenter;
import udacity.com.core.util.AlertUtils;
import udacity.com.core.util.ConstantsUtils;
import util.UtilSnackbar;

public class VeiculosMarcaActivity extends AppCompatActivity implements VeiculosMarcaContract.View, VeiculosMarcaContract.OnItemClickListener, SearchView.OnQueryTextListener {

    private static final String EXTRA_ID_MARCA = "idMarca";

    private VeiculosMarcaPresenter veiculosMarcaPresenter;
    private VeiculosMarcaAdapter veiculosMarcaAdapter;

    @BindView(R.id.emptyTextView)
    TextView emptyTextView;

    @BindView(R.id.listVeiculosMarca)
    RecyclerView recyclerView;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    String idMarca;

    String searchString = "";
    List<VeiculoMarca> veiculosMarcaPesquisa = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.veiculos_marca_activity_list);

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        veiculosMarcaAdapter = new VeiculosMarcaAdapter(this, getApplicationContext(), veiculosMarcaPesquisa);
        recyclerView.setAdapter(veiculosMarcaAdapter);

        veiculosMarcaPresenter = new VeiculosMarcaPresenter();
        veiculosMarcaPresenter.attachView(this);

        idMarca = getIntent().getExtras().getString(EXTRA_ID_MARCA);

        veiculosMarcaPresenter.onVeiculosMarcaFastNetworkingLibrary(marcaJsonObject());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public void showVeiculosMarca(List<VeiculoMarca> veiculoMarcaList) {
        veiculosMarcaPesquisa = veiculoMarcaList;
        veiculosMarcaAdapter.setValues(veiculoMarcaList);
    }

    @Override
    public void clickItem(VeiculoMarca veiculoMarca) {
        AlertUtils.alertView(this, "Selecione o ano do veículo:", R.mipmap.ic_launcher);
        //startActivity(VeiculosModeloAnoActivity.newVeiculosModeloAnoActivity(this, idMarca, veiculoMarca.getId()));
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
        UtilSnackbar.showSnakbarTipoUm(this.emptyTextView, ConstantsUtils.InfoLog.ERROR);
    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    public static Intent newVeiculosMarcaActivity(Context context, String idMarca) {
        Intent intent = new Intent(context, VeiculosMarcaActivity.class);
        intent.putExtra("idMarca", idMarca);
        return intent;
    }

    private JSONObject marcaJsonObject() {
        JSONObject marcaJsonObject = new JSONObject();
        try {
            marcaJsonObject.put("codigoMarca", idMarca);
            marcaJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TABELA_REFERENCIA,
                    ConstantsUtils.RequestParameters.VALOR_TABELA_REFERENCIA);
            marcaJsonObject.put(ConstantsUtils.RequestParameters.CODIGO_TIPO_VEICULO,
                    ConstantsUtils.RequestParameters.VALOR_TIPO_VEICULO);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return marcaJsonObject;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        veiculosMarcaPresenter.clearData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);

        MenuItem searchitem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchitem);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        TextView searchText = (TextView)
                searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);

        searchText.setTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHintTextColor(Color.parseColor("#FFFFFF"));
        searchText.setHint("Digite o nome do veículo...");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<VeiculoMarca> filteredModelList = filter(veiculosMarcaPesquisa, newText);
        if (filteredModelList.size() > 0) {
            veiculosMarcaAdapter.setFilter(filteredModelList);
            return true;
        } else {
            Toast.makeText(VeiculosMarcaActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private List<VeiculoMarca> filter(List<VeiculoMarca> veiculosMarca, String query) {

        query = query.toLowerCase();
        this.searchString = query;

        final List<VeiculoMarca> filteredModelList = new ArrayList<>();
        for (VeiculoMarca veiculoMarca : veiculosMarca) {
            final String text = veiculoMarca.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(veiculoMarca);
            }
        }
        veiculosMarcaAdapter = new VeiculosMarcaAdapter(this, getApplicationContext(), filteredModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(VeiculosMarcaActivity.this));
        recyclerView.setAdapter(veiculosMarcaAdapter);
        veiculosMarcaAdapter.notifyDataSetChanged();

        return filteredModelList;
    }


}
