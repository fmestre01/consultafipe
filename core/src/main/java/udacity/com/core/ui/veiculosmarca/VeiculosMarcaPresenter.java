/*
 * Copyright (c) Joaquim Ley 2016. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package udacity.com.core.ui.veiculosmarca;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import udacity.com.core.model.CombustivelModeloAno;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.AlertUtils;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.ConsultaFipeUtil;

public class VeiculosMarcaPresenter extends BasePresenter<VeiculosMarcaContract.View> implements VeiculosMarcaContract.Presenter {

    private List<VeiculoMarca> veiculosMarca;
    private List<CombustivelModeloAno> combustiveisModeloAno;

    @Override
    public void showAlertDialogAnoVeiculo(Context context, String title, String message, int icon, Intent intent, int layout, List<CombustivelModeloAno> anos) {
        AlertUtils.alertViewAnoVeiculo(context, title, message, icon, intent, layout, anos);
    }

    @Override
    public void onVeiculosMarcaFastNetworkingLibrary(final JSONObject veiculoMarcaJsonObject) {
        mView.showProgress();
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_VEICULOS_MARCA)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .addJSONObjectBody(veiculoMarcaJsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            veiculosMarca = new ArrayList<>();

                            JSONArray veiculosMarcaListJson = response.getJSONArray("Modelos");
                            int size = veiculosMarcaListJson.length();

                            for (int i = 0; i < size; i++) {
                                JSONObject objectVeiculoMarca = veiculosMarcaListJson.getJSONObject(i);
                                String id = objectVeiculoMarca.getString("Value");
                                String name = objectVeiculoMarca.getString("Label");

                                VeiculoMarca veiculoMarca = new VeiculoMarca();
                                veiculoMarca.setId(id);
                                veiculoMarca.setName(name);
                                veiculosMarca.add(veiculoMarca);
                            }

                            if (!isViewAttached()) return;
                            mView.hideProgress();

                            if (response.length() == 0) {
                                mView.showError(ConstantsUtils.ListLog.ERROR);
                                return;
                            } else {
                                mView.showVeiculosMarca(veiculosMarca);
                            }
                        } catch (Exception e) {
                            if (!isViewAttached()) return;
                            mView.hideProgress();
                            mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + e.getMessage());
                            Timber.e(ConstantsUtils.InfoLog.ERROR, e.fillInStackTrace());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + anError.getMessage());
                        Timber.e(ConstantsUtils.InfoLog.ERROR, anError.fillInStackTrace());
                    }
                });
    }

    @Override
    public void clearData() {
        veiculosMarca = null;
    }

    @Override
    public void loadCombustivelModelosAnos(JSONObject veiculosModeloJsonObject) {
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_ANO_MODELO)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .addJSONObjectBody(veiculosModeloJsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            combustiveisModeloAno = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String id = object.getString("Value");
                                String name = object.getString("Label");

                                CombustivelModeloAno combustivelModeloAno = new CombustivelModeloAno();
                                combustivelModeloAno.setId(id);
                                if (ConsultaFipeUtil.isVeiculoNovo(name)) {
                                    combustivelModeloAno.setName(ConstantsUtils.TipoVeiculo.VEICULO_ZERO_KM);
                                } else {
                                    combustivelModeloAno.setName(name);
                                }
                                combustiveisModeloAno.add(combustivelModeloAno);
                            }
                            if (!isViewAttached()) return;
                            mView.hideProgress();

                            if (response.length() == 0) {
                                mView.showError(ConstantsUtils.ListLog.ERROR);
                                return;
                            } else {
                                mView.showCombustivelAnoMocelo(combustiveisModeloAno);
                            }
                        } catch (Exception e) {
                            if (!isViewAttached()) return;
                            mView.hideProgress();
                            mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + e.getMessage());
                            Timber.e(ConstantsUtils.InfoLog.ERROR, e.fillInStackTrace());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + anError.getResponse().message());
                        Timber.e(ConstantsUtils.InfoLog.ERROR, anError.fillInStackTrace());
                    }
                });
    }
}