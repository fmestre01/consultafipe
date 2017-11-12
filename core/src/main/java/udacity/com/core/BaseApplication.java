package udacity.com.core;

import android.app.Application;

import timber.log.Timber;
import udacity.com.core.api.Api;
import udacity.com.core.api.ApiClient;
import udacity.com.core.data.AppDatabase;
import udacity.com.core.util.Constants;

public class BaseApplication extends Application {

    public static AppDatabase db;
    public static Api apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        db = AppDatabase.getDatabase(getApplicationContext());
        apiService = ApiClient.makeFipeService();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.tag(BaseApplication.class.getName()).i(Constants.Application.INITAPPLICATION);
    }
}
