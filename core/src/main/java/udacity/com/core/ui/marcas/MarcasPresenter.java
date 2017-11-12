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

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.model.Marca;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.Constants;

public class MarcasPresenter extends BasePresenter<MarcasContract.View> implements MarcasContract.Presenter {

    private Gson gson = new Gson();

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

                    //for (int i = 0; i < response.size(); i++) {
                    //String marcaEntity = gson.toJson(response.get(i));
                    //BaseApplication.db.marcaDao().insertMarca(gson.fromJson(marcaEntity, MarcaEntity.class));
                    //}

                    if (response.isEmpty()) {
                        mView.showError(Constants.ListLog.ERROR);
                        return;
                    } else {
                        mView.showMarcas(response);
                    }
                } catch (Exception e) {
                    mView.showError(e.getMessage());
                    Timber.e(Constants.InfoLog.ERROR, e.getMessage());
                }
            }

            @Override
            public void onUnauthorized() {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showUnauthorizedError();
                Timber.e(Constants.InfoLog.UNAUTHORIZED);
            }

            @Override
            public void onFailed(Throwable throwable) {
                if (!isViewAttached()) return;
                mView.hideProgress();
                mView.showError(Constants.InfoLog.ERROR + "-" + throwable.getMessage());
                Timber.e(Constants.InfoLog.ERROR, throwable.fillInStackTrace());
            }
        });
    }
}