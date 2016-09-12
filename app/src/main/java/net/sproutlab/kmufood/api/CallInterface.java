package net.sproutlab.kmufood.api;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by kde713 on 2016. 9. 10..
 */
public interface CallInterface {
    @GET("/menujson")
    Call<String> downloadMenu();
}
