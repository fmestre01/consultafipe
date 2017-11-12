package udacity.com.core.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import udacity.com.core.model.Marca;
import udacity.com.core.model.Veiculo;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.model.VeiculoModeloAno;

public interface Api {
    @GET("marcas.json")
    Call<List<Marca>> getMarcas();

    @GET("veiculos/{id_marca}.json")
    Call<List<VeiculoMarca>> getVeiculosMarca(@Path("id_marca") String idMarca);

    @GET("veiculo/{id_marca}/{id_modelo_ano}.json")
    Call<List<VeiculoModeloAno>> getVeiculosModeloAno(@Path("id_marca") String idMarca, @Path("id_modelo_ano") String idModeloAno);

    @GET("veiculo/{id_marca}/{id_modelo_ano}/{id_veiculo}.json")
    Call<Veiculo> getVeiculoDetalhe(@Path("id_marca") String idMarca, @Path("id_modelo_ano") String idModeloAno, @Path("id_veiculo") String idVeiculo);
}
