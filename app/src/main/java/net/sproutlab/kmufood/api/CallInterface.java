package net.sproutlab.kmufood.api;

import net.sproutlab.kmufood.api.models.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kde713 on 2016. 9. 10..
 */
public interface CallInterface {
    @GET("/menujson")
    Call<String> downloadMenu();

    @GET("/menu/menujson.php")
    Call<ApiResponse> coopApi(
            @Query("sdate") String queryFrom,
            @Query("edate") String queryTo
    );
}
