package com.dynast.dhwallet.api;

import com.dynast.dhwallet.model.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface APIService {


    //sign in call
    @FormUrlEncoded
    @POST("/Android/v1/userLogin.php")
    Call<Result> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/Android/v1/registerUser.php")
    Call<Result> createUser(
            @Field("name") String name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("mobile") String mobile);
}
