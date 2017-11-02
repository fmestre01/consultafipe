package udacity.com.core;

import android.app.Application;

import timber.log.Timber;
import udacity.com.core.util.Constants;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.tag(BaseApplication.class.getName()).i(Constants.Application.INITAPPLICATION);
    }
}
