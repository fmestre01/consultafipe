package udacity.com.core.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import udacity.com.core.BuildConfig;
import udacity.com.core.util.ConstantsUtils;

public class ApiClient {

    public static OkHttpClient makeOkHttpClient() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(ConstantsUtils.Api.HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(ConstantsUtils.Api.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(ConstantsUtils.Api.HTTP_READ_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(makeLoggingInterceptor());
        return httpClientBuilder.build();
    }

    private static HttpLoggingInterceptor makeLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }
}
