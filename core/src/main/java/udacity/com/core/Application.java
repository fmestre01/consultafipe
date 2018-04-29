package udacity.com.core;

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jacksonandroidnetworking.JacksonParserFactory;
import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import timber.log.Timber;
import udacity.com.core.api.ApiClient;
import udacity.com.core.model.TabelaReferencia;
import udacity.com.core.util.ConstantsUtils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Application extends android.app.Application {

    public static TabelaReferencia codigoTabelaReferencia;
    public static String codigoTipoVeiculo;
    public static FirebaseAnalytics firebaseAnalytics;
    public static List<TabelaReferencia> tabelasReferencias;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        AndroidNetworking.initialize(this, ApiClient.makeOkHttpClient());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        Timber.tag(Application.class.getName()).i(ConstantsUtils.Application.INITAPPLICATION);

        LeakCanary.install(this);
    }
}
