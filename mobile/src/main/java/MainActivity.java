import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.Api;
import udacity.com.core.api.ApiClient;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.data.entity.MarcaEntity;
import udacity.com.core.data.entity.VeiculoEntity;
import udacity.com.core.data.entity.VeiculoMarcaEntity;
import udacity.com.core.model.Marca;
import udacity.com.core.model.Veiculo;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.util.Constants;
import udacity.com.core.util.SharedPrefsUtils;

public class MainActivity extends AppCompatActivity {

    private Api apiService = ApiClient.makeFipeService();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        veiculosMarcaResponse();

        SharedPrefsUtils.setStringPreference(getApplicationContext(), "TESTE", "testesss");

        SharedPrefsUtils.getStringPreference(getApplicationContext(), "TESTE");
    }

    private void marcasResponse() {
        Call<List<Marca>> call = apiService.getMarcas();
        call.enqueue(new RemoteCallback<List<Marca>>() {
            @Override
            public void onSuccess(List<Marca> response) {
                try {
                    JSONArray marcasJson = new JSONObject(gson.toJson(response)).getJSONArray("body");

                    for (int i = 0; i < marcasJson.length(); i++) {
                        MarcaEntity marcaEntity = gson.fromJson(marcasJson.getJSONObject(i).toString(), MarcaEntity.class);
                        BaseApplication.db.marcaDao().insertMarca(marcaEntity);
                        Timber.i(Constants.InfoLog.INFO, marcaEntity);
                    }
                } catch (Exception e) {
                    Timber.e(Constants.InfoLog.ERROR, e.fillInStackTrace());
                }
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void veiculosMarcaResponse() {
        Call<List<VeiculoMarca>> call = apiService.getVeiculosMarca("21");
        call.enqueue(new RemoteCallback<List<VeiculoMarca>>() {
            @Override
            public void onSuccess(List<VeiculoMarca> response) {
                try {
                    for (int i = 0; i < response.size(); i++) {
                        VeiculoMarcaEntity veiculoMarcaEntity = gson.fromJson(response.get(i).toString(), VeiculoMarcaEntity.class);
                        BaseApplication.db.veiculoDao().insertVeiculosMarca(veiculoMarcaEntity);
                    }

                    Log.d("", String.valueOf(response.size()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("", String.valueOf(throwable.getStackTrace()));
                //UtilSnackbar.showSnakbarTipoUm(textViewExample, "Falha ao consultar veiculos...");
            }
        });
    }

    private void veiculosModeloAnoResponse() {
        Call<List<VeiculoModeloAno>> call = apiService.getVeiculosModeloAno("21", "4828");
        call.enqueue(new RemoteCallback<List<VeiculoModeloAno>>() {
            @Override
            public void onSuccess(List<VeiculoModeloAno> response) {
                Log.d("", String.valueOf(response.size()));
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });
    }

    private void veiculoDetalhe() {
        Call<Veiculo> call = apiService.getVeiculoDetalhe("21", "4828", "2013-1");
        call.enqueue(new RemoteCallback<Veiculo>() {
            @Override
            public void onSuccess(Veiculo response) {
                String jsonResponse = gson.toJson(response);
                VeiculoEntity veiculoEntity = gson.fromJson(jsonResponse, VeiculoEntity.class);

                Veiculo veiculoExistente = BaseApplication.db.veiculoDao().veiculoPorId(response.getId());
                if (veiculoExistente != null) {
                    BaseApplication.db.veiculoDao().updateVeiculo(veiculoEntity);
                } else {
                    BaseApplication.db.veiculoDao().insertVeiculo(veiculoEntity);
                }

                List<VeiculoEntity> veiculoEntities = BaseApplication.db.veiculoDao().todosVeiculos();
                for (int i = 0; i < veiculoEntities.size(); i++) {
                    Log.d("", veiculoEntities.get(i).toString());
                }
            }

            @Override
            public void onUnauthorized() {
                Log.d("", "onUnauthorized");
            }

            @Override
            public void onFailed(Throwable throwable) {
                Log.d("", throwable.getCause().getMessage());
            }
        });
    }
}
