package miw.upm.es.memegenerator.network;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import miw.upm.es.memegenerator.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Enrique on 10/11/2016.
 */

public class MemeGeneratorFactory {

    private static final String API_KEY = "X-Mashape-Key";
    private static final String API_KEY_VALUE = "YlehhIHsf0mshx6QukY1UtQa00fPp1uwSR1jsnyfyzmDmlio6x";

    public static MemeGeneratorService create(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(5, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(interceptor);
        }

        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader(API_KEY, API_KEY_VALUE).build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = builder.build();

        Retrofit retrofit =
                new Retrofit.Builder()
                        .baseUrl(MemeGeneratorService.ENDPOINT)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();

        return retrofit.create(MemeGeneratorService.class);
    }

}

