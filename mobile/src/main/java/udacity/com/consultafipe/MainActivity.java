package udacity.com.consultafipe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import udacity.com.core.model.Marca;
import udacity.com.core.model.Veiculo;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.rest.Api;
import udacity.com.core.rest.ApiClient;
import udacity.com.core.rest.RemoteCallback;

public class MainActivity extends AppCompatActivity {

    private Api apiService = ApiClient.makeFipeService();
    private List<Marca> marcas;
    private List<Veiculo> veiculosMarca;
    private List<VeiculoModeloAno> veiculosModeloAno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //marcasResponse();
        //veiculosResponse();
        veiculosModeloAnoResponse();
    }

    private void marcasResponse() {
        Call<List<Marca>> call = apiService.getMarcas();
        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                int code = response.code();
                marcas = response.body();
                for (int i = 0; i < marcas.size(); i++) {
                    Log.d(marcas.get(i).getId(), marcas.get(i).getFipeName());
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {

            }
        });
    }

    private void veiculosResponse() {
        Call<List<Veiculo>> call = apiService.getVeiculosMarca("21");
        call.enqueue(new RemoteCallback<List<Veiculo>>() {
            @Override
            public void onSuccess(List<Veiculo> response) {
                veiculosMarca = response;
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

    private void veiculosModeloAnoResponse() {
        Call<List<VeiculoModeloAno>> call = apiService.getVeiculosModeloAno("21", "4828");
        call.enqueue(new RemoteCallback<List<VeiculoModeloAno>>() {
            @Override
            public void onSuccess(List<VeiculoModeloAno> response) {
                veiculosModeloAno = response;
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
}
