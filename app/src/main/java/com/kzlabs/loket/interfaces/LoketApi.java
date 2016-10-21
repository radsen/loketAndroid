package com.kzlabs.loket.interfaces;

import com.kzlabs.loket.models.Auth;
import com.kzlabs.loket.models.DataWrapper;
import com.kzlabs.loket.models.Pocket;
import com.kzlabs.loket.models.ResponsePocket;
import com.kzlabs.loket.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
    Call<ResponsePocket> createPocket(@Field("destination") String destination,
                                      @Field("value") float value,
                                      @Field("description") String description);

    @PUT("pockets/{id}/{task}")
    Call<ResponsePocket> updatePocketTransaction(@Path("id") String id,
                                                 @Path("task") String task);
}
