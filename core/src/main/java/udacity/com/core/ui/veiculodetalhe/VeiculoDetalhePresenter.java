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

package udacity.com.core.ui.veiculodetalhe;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

import timber.log.Timber;
import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.ConsultaFipeUtils;

public class VeiculoDetalhePresenter extends BasePresenter<VeiculoDetalheContract.View> implements VeiculoDetalheContract.Presenter {

    @Override
    public void onVeiculoDetalheRequested(JSONObject veiculoDetalhejsonObject) {
        mView.showProgress();
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_VEICULO_DETALHE)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .addJSONObjectBody(veiculoDetalhejsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (!isViewAttached()) return;
                            mView.hideProgress();

                            if (response.length() == 0) {
                                mView.showError(ConstantsUtils.ListLog.ERROR);
                            } else {
                                Veiculo v = new Veiculo();
                                v.setMarca(response.getString("Marca"));
                                v.setModelo(response.getString("Modelo"));
                                if (ConsultaFipeUtils.isVeiculoNovo(response.getString("AnoModelo"))) {
                                    v.setAnoModelo(ConstantsUtils.TipoVeiculo.VEICULO_ZERO_KM);
                                } else {
                                    v.setAnoModelo(response.getString("AnoModelo"));
                                }

                                v.setValor(response.getString("Valor"));
                                v.setCombustivel(response.getString("Combustivel"));
                                v.setDataConsulta(response.getString("DataConsulta"));
                                v.setCodigoFipe(response.getString("CodigoFipe"));
                                mView.showVeiculoDetalhe(v);
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
}