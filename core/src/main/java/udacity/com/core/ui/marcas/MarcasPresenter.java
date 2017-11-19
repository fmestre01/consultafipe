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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.ConstantsUtils;

public class MarcasPresenter extends BasePresenter<MarcasContract.View> implements MarcasContract.Presenter {

    private Gson gson = new Gson();
    private List<Marca> marcas;

    @Override
    public void onMarcasRequested() {
        mView.showProgress();
        Call<List<Marca>> call = BaseApplication.apiService.getMarcas();
        call.enqueue(new RemoteCallback<List<Marca>>() {
            @Override
            public void onSuccess(List<Marca> response) {
                try {
                    if (!isViewAttached()) return;
                    mView.hideProgress();

                    if (response.isEmpty()) {
                        mView.showError(ConstantsUtils.ListLog.ERROR);
                        return;
                    } else {
                        mView.showMarcas(response);
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
    public void onMarcasRequestedFastNetworkingLibrary(JSONObject marcaJsonObject) {
        AndroidNetworking.post(ConstantsUtils.Urls.SITE_FIPE + ConstantsUtils.Urls.OP_KEY_MARCAS)
                .addHeaders(ConstantsUtils.Urls.HEADER_REFERER, ConstantsUtils.Urls.HEADER_REFERER_VALUE)
                .addJSONObjectBody(marcaJsonObject)
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
        marcas = null;
    }
}