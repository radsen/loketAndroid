package com.kzlabs.loket.interfaces;

import com.kzlabs.loket.models.Auth;
import com.kzlabs.loket.models.DataWrapper;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by radsen on 10/18/16.
 */
public interface LoketApi {
    @FormUrlEncoded
    @POST("login")
    Call<Auth> authentication(@Field("apiUrl") String apiUrl,
                              @Field("credentials") String credentials);

    @GET("pockets")
    Call<DataWrapper> getPockets();

    @FormUrlEncoded
    @POST("pockets")
    Call<Pocket> createPocket(@Field("destination") String destination,
                              @Field("value") float value,
                              @Field("description") String description);
}
