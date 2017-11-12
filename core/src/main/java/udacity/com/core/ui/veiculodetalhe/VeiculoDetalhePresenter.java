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

import com.google.gson.Gson;

import retrofit2.Call;
import timber.log.Timber;
import udacity.com.core.BaseApplication;
import udacity.com.core.api.RemoteCallback;
import udacity.com.core.model.Veiculo;
import udacity.com.core.ui.base.BasePresenter;
import udacity.com.core.util.Constants;

public class VeiculoDetalhePresenter extends BasePresenter<VeiculoDetalheContract.View> implements VeiculoDetalheContract.Presenter {

    private Gson gson = new Gson();

    public void onVeiculoDetalheRequested(String idMarca, String idModeloAno, String idVeiculo) {
        mView.showProgress();
        Call<Veiculo> call = BaseApplication.apiService.getVeiculoDetalhe(idMarca, idModeloAno, idVeiculo);
        call.enqueue(new RemoteCallback<Veiculo>() {
            @Override
            public void onSuccess(Veiculo response) {
                try {
                    if (!isViewAttached()) return;
                    mView.hideProgress();

                    if (response == null) {
                        mView.showError(Constants.ListLog.ERROR);
                        return;
                    } else {
                        mView.showVeiculoDetalhe(response);
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