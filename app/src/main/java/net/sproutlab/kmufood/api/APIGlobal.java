package net.sproutlab.kmufood.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kde713 on 2016. 9. 10..
 */
public class APIGlobal {
    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://kmucoop.kookmin.ac.kr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static CallInterface callInterface = retrofit.create(CallInterface.class);
}
