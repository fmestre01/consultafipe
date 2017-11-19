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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.model.VeiculoMarca;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.ConstantsUtils;

public class VeiculosMarcaPresenter extends BasePresenter<VeiculosMarcaContract.View> implements VeiculosMarcaContract.Presenter {

    private Gson gson = new Gson();
    private List<VeiculoMarca> veiculosMarca;

    public void onVeiculosMarcaRequested(String idMarca) {
        mView.showProgress();
        Call<List<VeiculoMarca>> call = BaseApplication.apiService.getVeiculosMarca(idMarca);
        call.enqueue(new RemoteCallback<List<VeiculoMarca>>() {
            @Override
            public void onSuccess(List<VeiculoMarca> response) {
                try {
                    if (!isViewAttached()) return;
                    mView.hideProgress();

                    if (response.isEmpty()) {
                        mView.showError(ConstantsUtils.ListLog.ERROR);
                        return;
                    } else {
                        mView.showVeiculosMarca(response);
                    }
                } catch (Exception e) {
                    mView.showError(e.getMessage());
                    Timber.e(ConstantsUtils.InfoLog.ERROR, e.getMessage());
                }
            }

            @Override
            public void onUnauthorized() {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showUnauthorizedError();
                Timber.e(ConstantsUtils.InfoLog.UNAUTHORIZED);
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showError(ConstantsUtils.InfoLog.ERROR + "-" + throwable.getMessage());
                Timber.e(ConstantsUtils.InfoLog.ERROR, throwable.fillInStackTrace());
            }
        });
    }

    @Override
    public void onVeiculosMarcaFastNetworkingLibrary(final JSONObject veiculoMarcaJsonObject) {
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
                                JSONObject object = veiculosMarcaListJson.getJSONObject(i);
                                String id = object.getString("Value");
                                String name = object.getString("Label");

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
                            mView.showError(e.getMessage());
                            Timber.e(ConstantsUtils.InfoLog.ERROR, e.getMessage());
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
}