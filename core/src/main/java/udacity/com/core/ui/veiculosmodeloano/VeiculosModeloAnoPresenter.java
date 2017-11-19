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

package udacity.com.core.ui.veiculosmodeloano;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.model.VeiculoModeloAno;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.ConstantsUtils;

public class VeiculosModeloAnoPresenter extends BasePresenter<VeiculosModeloAnoContract.View> implements VeiculosModeloAnoContract.Presenter {

    private Gson gson = new Gson();

    public void onVeiculosModeloAnoRequested(String idMarca, String idModeloAno) {
        mView.showProgress();
        Call<List<VeiculoModeloAno>> call = BaseApplication.apiService.getVeiculosModeloAno(idMarca, idModeloAno);
        call.enqueue(new RemoteCallback<List<VeiculoModeloAno>>() {
            @Override
            public void onSuccess(List<VeiculoModeloAno> response) {
                try {
                    if (!isViewAttached()) return;
                    mView.hideProgress();

                    if (response.isEmpty()) {
                        mView.showError(ConstantsUtils.ListLog.ERROR);
                        return;
                    } else {
                        mView.showVeiculosModeloAno(response);
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
}