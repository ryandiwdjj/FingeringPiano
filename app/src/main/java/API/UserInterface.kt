package API

import Models.login
import Models.user
import Models.video
import retrofit2.Call
import retrofit2.http.*
import java.util.*

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
//    @FormUrlEncoded
    @GET("user/{id}/")
    fun showUser(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<user>

    //update user
    @FormUrlEncoded
    @POST("user/update/{id}")
    fun updateUser(@Path("id")id: Int,
                   @Field("name")name: String,
                   @Field("email")email: String,
                   @Field("password")password: String,
                   @Field("dateOfbirth")dataOfbirth: String,
                   @Field("address")address: String,
                   @Field("gender")gender: String,
                   @Field("phoneNumber")phoneNumber: String,
                   @Field("city")city: String) :Call<user>

}