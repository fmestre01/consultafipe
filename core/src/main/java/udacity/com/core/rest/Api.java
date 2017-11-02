package udacity.com.core.rest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import udacity.com.core.model.Marca;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.model.VeiculoModeloAno;

public interface Api {
    @GET("marcas.json")
    Call<List<Marca>> getMarcas();

    @GET("veiculos/{id_marca}.json")
    Call<List<VeiculoMarca>> getVeiculosMarca(@Path("id_marca") String idMarca);

    @GET("veiculo/{id_marca}/{id_veiculo}.json")
    Call<List<VeiculoModeloAno>> getVeiculosModeloAno(@Path("id_marca") String idMarca, @Path("id_veiculo") String idveiculo);

    @GET("veiculo/{id_marca}/{id_veiculo}/{codigo_veiculo}.json")
    Call<List<VeiculoModeloAno>> getVeiculoDetalhe(@Path("id_marca") String idMarca, @Path("id_veiculo") String codigoVeiculo);
}
