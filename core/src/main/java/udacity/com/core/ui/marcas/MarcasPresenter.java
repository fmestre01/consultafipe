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

package udacity.com.core.ui.marcas;

import android.content.Context;
import android.content.Intent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
import udacity.com.core.model.Marca;
import udacity.com.core.model.TabelaReferencia;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.AlertUtils;
import udacity.com.core.util.ConstantsUtils;
import udacity.com.core.util.ListUtils;

public class MarcasPresenter extends BasePresenter<MarcasContract.View> implements MarcasContract.Presenter {

    private List<Marca> marcas;
    private List<TabelaReferencia> tabelaReferenciaAnos;

    @Override
    public void onMarcasRequestedFastNetworkingLibrary(JSONObject marcaJsonObject) {
        mView.showProgress();
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_MARCAS)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .addJSONObjectBody(marcaJsonObject)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            marcas = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String id = object.getString("Value");
                                String name = object.getString("Label");

                                Marca marca = new Marca();
                                marca.setId(id);
                                marca.setName(name);
                                marcas.add(marca);
                            }

                            if (!isViewAttached()) return;
                            mView.hideProgress();

                            if (response.length() == 0) {
                                mView.showError(ConstantsUtils.ListLog.ERROR);
                                return;
                            } else {
                                mView.showMarcas(marcas);
                            }
                        } catch (Exception e) {
                            if (!isViewAttached()) return;
                            mView.hideProgress();
                            mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + e.getStackTrace());
                            Timber.e(ConstantsUtils.InfoLog.ERROR, e.fillInStackTrace());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + anError.getErrorBody());
                        Timber.e(ConstantsUtils.InfoLog.ERROR, anError.fillInStackTrace());
                    }
                });
    }

    @Override
    public void clearData() {
        marcas = null;
        tabelaReferenciaAnos = null;
    }

    @Override
    public void showAlertDialogPeriodoReferencia(Context context, String title, String message, int icon, int layout, List<TabelaReferencia> anosReferencia, Intent intent) {
        AlertUtils.alertViewAnoReferencia(context, title, message, icon, layout, anosReferencia, intent);
    }

    @Override
    public void showAlertDialogTipoVeiculo(Context context, String title, String message, int icon, int layout, Intent intent) {
        AlertUtils.alertViewTipoVeiculo(context, title, message, icon, layout, intent);
    }

    @Override
    public void onTabelaReferenciaLoadFromFile(InputStream in) {
        try {
            String anoReferenciaJson = ListUtils.readLocalJsonFile(in);
            JSONObject jo = new JSONObject(anoReferenciaJson);
            JSONArray arrayAnoReferencia = jo.getJSONArray("anosReferencia");

            tabelaReferenciaAnos = new ArrayList<>();

            for (int i = 0; i < arrayAnoReferencia.length(); i++) {
                JSONObject object = null;
                try {
                    object = arrayAnoReferencia.getJSONObject(i);
                    String codigo = object.getString("Codigo");
                    String mes = object.getString("Mes");
                    TabelaReferencia tabelaReferencia = new TabelaReferencia();
                    tabelaReferencia.setId(codigo);
                    tabelaReferencia.setMes(mes);
                    tabelaReferenciaAnos.add(tabelaReferencia);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mView.showTabelaReferencia(tabelaReferenciaAnos);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTabelaReferenciaRequestedFastNetworkingLibrary() {
        mView.showProgress();
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_TABELA_REFERENCIA)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            tabelaReferenciaAnos = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject object = response.getJSONObject(i);
                                String id = object.getString("Codigo");
                                String name = object.getString("Mes");

                                TabelaReferencia tabelaReferencia = new TabelaReferencia();
                                tabelaReferencia.setId(id);
                                tabelaReferencia.setMes(name);
                                tabelaReferenciaAnos.add(tabelaReferencia);
                            }

                            if (!isViewAttached()) return;
                            mView.hideProgress();

                            if (response.length() == 0) {
                                mView.showEmpty();
                                return;
                            } else {
                                mView.showTabelaReferencia(tabelaReferenciaAnos);
                            }
                        } catch (Exception e) {
                            if (!isViewAttached()) return;
                            mView.hideProgress();
                            mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + e.getStackTrace());
                            Timber.e(ConstantsUtils.InfoLog.ERROR, e.fillInStackTrace());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        if (!isViewAttached()) return;
                        mView.hideProgress();
                        mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + anError.getErrorBody());
                        Timber.e(ConstantsUtils.InfoLog.ERROR, anError.fillInStackTrace());
                    }
                });
    }
}