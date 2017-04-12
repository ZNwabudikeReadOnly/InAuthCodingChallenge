package com.drawingboardapps.mainsdk.sdk.hidden;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Zach on 4/11/2017.
 */

public interface HttpApi {

    @GET("rows.json")
    Call<ResponseBody> makeCall(@Query("accessType") String query);
}
