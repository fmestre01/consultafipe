package udacity.com.core;

import android.app.Application;

import timber.log.Timber;
import udacity.com.core.data.AppDatabase;
import udacity.com.core.util.Constants;

public class BaseApplication extends Application {

    public static AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        db = AppDatabase.getDatabase(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.tag(BaseApplication.class.getName()).i(Constants.Application.INITAPPLICATION);
    }
}
