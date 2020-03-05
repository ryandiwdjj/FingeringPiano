package com.fingering.fingeringpiano.API

import com.fingering.fingeringpiano.Models.login
import com.fingering.fingeringpiano.Models.updateResponse
import com.fingering.fingeringpiano.Models.userResponse
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    //Daftar user
    @FormUrlEncoded
    @POST("register")
    fun registerUser(@Field("name")name: String,
                     @Field("email")email: String,
                     @Field("password")password: String,
                     @Field("dateofbirth")dataOfbirth: String,
                     @Field("address")address: String,
                     @Field("gender")gender: String,
                     @Field("phoneNumber")phoneNumber: String,
                     @Field("city")city: String) :Call<login>

    //Login user
    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(@Field("email") email: String,
                    @Field("password") password: String): Call<login>

    //show user
    @GET("user/")
    fun showUser(@Header("Authorization") authHeader: String): Call<userResponse>

    //update user
    @FormUrlEncoded
    @POST("updatedata")
    fun updateUser(@Header("Authorization") authHeader: String,
                   @Field("name")name: String,
                   @Field("email")email: String,
                   @Field("dateofbirth")dataofbirth: String,
                   @Field("address")address: String,
                   @Field("gender")gender: String,
                   @Field("phoneNumber")phoneNumber: String,
                   @Field("city")city: String) :Call<updateResponse>

}