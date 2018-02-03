package udacity.com.core;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;
import udacity.com.core.api.ApiClient;
import udacity.com.core.data.AppDatabase;
import udacity.com.core.model.AnoReferencia;
import udacity.com.core.util.ConstantsUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BaseApplication extends Application {

    public static AppDatabase db;
    public static AnoReferencia codigoTabelaReferencia;
    public static String codigoTipoVeiculo;

    @Override
    public void onCreate() {
        super.onCreate();

        db = AppDatabase.getDatabase(getApplicationContext());

        AndroidNetworking.initialize(this, ApiClient.makeOkHttpClient());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/gtw.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Timber.tag(BaseApplication.class.getName()).i(ConstantsUtils.Application.INITAPPLICATION);

        LeakCanary.install(this);
    }
}
