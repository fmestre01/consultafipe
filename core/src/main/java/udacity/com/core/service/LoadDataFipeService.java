package udacity.com.core.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import timber.log.Timber;
import udacity.com.core.util.Constants;

public class LoadDataFipeService extends Service {

    private RetrieveDataFromApi retrieveDataFromApi = new RetrieveDataFromApi();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.i(Constants.InfoLog.INFO + "----INICIANDO SERVIÇO DE DOWNLOAD ", RetrieveDataFromApi.class.getName() + "----\n");
        retrieveDataFromApi.initSubProcessService();

        //if (!retrieveDataFromApi.retrieveAndSaveLocalMarcas()) {
            //stopService(new Intent(this, LoadDataFipeService.class));
        //}

        //if (!retrieveDataFromApi.retrieveAndSaveLocalVeiculosMarca()) {
            //stopService(new Intent(this, LoadDataFipeService.class));
        //}

        //if (!retrieveDataFromApi.retrieveAndSaveLocalVeiculosModeloAno()) {
            //stopService(new Intent(this, LoadDataFipeService.class));
        //}

        //stopService(new Intent(this, LoadDataFipeService.class));

        //retrieveDataFromApi.retrieveAndSaveLocalMarcas();
        //retrieveDataFromApi.retrieveAndSaveLocalVeiculosMarca();
        //retrieveDataFromApi.retrieveAndSaveLocalVeiculosModeloAno();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
