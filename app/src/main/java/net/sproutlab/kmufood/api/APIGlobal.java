package net.sproutlab.kmufood.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by kde713 on 2016. 9. 10..
 */
public class APIGlobal {
    final static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build();
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://kmufood-server.herokuapp.com")
            .client(okHttpClient)
            .addConverterFactory(new ToStringConverterFactory())
            .build();
    public static CallInterface callInterface = retrofit.create(CallInterface.class);
}
