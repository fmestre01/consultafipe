package udacity.com.core.service;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.data.entity.MarcaEntity;
import udacity.com.core.data.entity.VeiculoMarcaEntity;
import udacity.com.core.data.entity.VeiculoModeloAnoEntity;
import udacity.com.core.model.Marca;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.util.Constants;

public class RetrieveDataFromApi implements ApiService {

    private Gson gson = new Gson();
    private boolean marcasLoadSucess;
    private boolean veiculosMarcaLoadSucess;
    private boolean veiculosModeloAnoLoadSucess;

    private List<MarcaEntity> marcasLocal;
    private int marcasLocalSize;
    private List<VeiculoMarcaEntity> veiculosMarcaLocal;
    private int veiculosMarcaLocalSize;

    public RetrieveDataFromApi() {
    }

    public void initSubProcessService() {
        marcasLocal = BaseApplication.db.marcaDao().todasMarcas();
        marcasLocalSize = marcasLocal.size();
        veiculosMarcaLocal = BaseApplication.db.veiculoMarcaDao().todosVeiculosMarca();
        veiculosMarcaLocalSize = veiculosMarcaLocal.size();
    }

    @Override
    public boolean retrieveAndSaveLocalMarcas() {
        Call<List<Marca>> call = BaseApplication.apiService.getMarcas();
        call.enqueue(new RemoteCallback<List<Marca>>() {
            @Override
            public void onSuccess(List<Marca> response) {
                try {
                    if (response.isEmpty()) {
                        return;
                    } else {
                        for (int i = 0; i < response.size(); i++) {
                            String marcaEntity = gson.toJson(response.get(i));
                            BaseApplication.db.marcaDao().insertMarca(gson.fromJson(marcaEntity, MarcaEntity.class));
                            Timber.i(Constants.InfoLog.INFO + "---INSERT MARCA---" + response.get(i).getId(), RetrieveDataFromApi.class.getName() + "\n");
                        }
                        marcasLoadSucess = true;
                    }
                } catch (Exception e) {
                    marcasLoadSucess = false;
                    Timber.e(Constants.InfoLog.ERROR);
                }
            }

            @Override
            public void onUnauthorized() {
                marcasLoadSucess = false;
                Timber.e(Constants.InfoLog.UNAUTHORIZED);
            }

            @Override
            public void onFailed(Throwable throwable) {
                marcasLoadSucess = false;
                Timber.e(Constants.InfoLog.ERROR);
            }
        });
        return marcasLoadSucess;
    }

    @Override
    public boolean retrieveAndSaveLocalVeiculosMarca() {
        for (int i = 0; i < marcasLocalSize; i++) {
            Call<List<VeiculoMarca>> call = BaseApplication.apiService.getVeiculosMarca(String.valueOf(marcasLocal.get(i).getId()));
            call.enqueue(new RemoteCallback<List<VeiculoMarca>>() {
                @Override
                public void onSuccess(List<VeiculoMarca> response) {
                    try {
                        if (response.isEmpty()) {
                            return;
                        } else {
                            for (int i = 0; i < response.size(); i++) {
                                String veiculoMarca = gson.toJson(response.get(i));
                                BaseApplication.db.veiculoMarcaDao().insertVeiculosMarca(gson.fromJson(veiculoMarca, VeiculoMarcaEntity.class));
                                Timber.i(Constants.InfoLog.INFO + "---INSERT VEICULO MARCA---" + response.get(i).getId(), RetrieveDataFromApi.class.getName() + "\n");
                            }
                        }
                    } catch (Exception e) {
                        Timber.e(Constants.InfoLog.ERROR);
                    }
                    veiculosMarcaLoadSucess = true;
                }

                @Override
                public void onUnauthorized() {
                    veiculosMarcaLoadSucess = false;
                    Timber.e(Constants.InfoLog.UNAUTHORIZED);
                }

                @Override
                public void onFailed(Throwable throwable) {
                    veiculosMarcaLoadSucess = false;
                    Timber.e(Constants.InfoLog.ERROR);
                }
            });
        }
        return veiculosMarcaLoadSucess;
    }

    @Override
    public boolean retrieveAndSaveLocalVeiculosModeloAno() {
        for (int z = 0; z < marcasLocalSize; z++) {
            for (int i = 0; i < veiculosMarcaLocalSize; i++) {
                Call<List<VeiculoModeloAno>> call = BaseApplication.apiService.getVeiculosModeloAno(String.valueOf(marcasLocal.get(z).getId()), String.valueOf(veiculosMarcaLocal.get(i).getId()));
                call.enqueue(new RemoteCallback<List<VeiculoModeloAno>>() {
                    @Override
                    public void onSuccess(List<VeiculoModeloAno> response) {
                        try {
                            if (response.isEmpty()) {
                                return;
                            } else {
                                for (int i = 0; i < response.size(); i++) {
                                    String veiculoAnoModelo = gson.toJson(response.get(i));
                                    BaseApplication.db.veiculoModeloAnoDao().insertVeiculosModeloAno(gson.fromJson(veiculoAnoModelo, VeiculoModeloAnoEntity.class));
                                    Timber.i(Constants.InfoLog.INFO + "---INSERT VEICULO ANO MODELO---" + response.get(i).toString(), RetrieveDataFromApi.class.getName() + "\n");
                                }
                            }
                        } catch (Exception e) {
                            Timber.e(Constants.InfoLog.ERROR);
                        }
                        veiculosModeloAnoLoadSucess = true;
                    }

                    @Override
                    public void onUnauthorized() {
                        veiculosModeloAnoLoadSucess = false;
                        Timber.e(Constants.InfoLog.UNAUTHORIZED);
                    }

                    @Override
                    public void onFailed(Throwable throwable) {
                        veiculosModeloAnoLoadSucess = false;
                        Timber.e(Constants.InfoLog.ERROR);
                    }
                });
            }
        }
        return veiculosModeloAnoLoadSucess;
    }
}
