package marcaslist;

import android.app.SearchManager;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import udacity.com.consultafipe.R;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.marcas.MarcasContract;
import udacity.com.core.ui.marcas.MarcasPresenter;
import udacity.com.core.util.Constants;
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

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        marcasPresenter.onMarcasRequested();
                    }
                }
        );

        marcasPresenter.onMarcasRequested();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void showMarcas(List<Marca> marcaList) {
        marcasPesquisa = marcaList;
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
        UtilSnackbar.showSnakbarTipoUm(this.emptyTextView, Constants.InfoLog.ERROR);
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
            Toast.makeText(MarcasActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private List<Marca> filter(List<Marca> marcas, String query) {

        query = query.toLowerCase();
        this.searchString = query;

        final List<Marca> filteredModelList = new ArrayList<>();
        for (Marca marca : marcas) {
            final String text = marca.getFipe_name().toLowerCase();
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
        searchText.setHint("Digite a marca...");
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

}
