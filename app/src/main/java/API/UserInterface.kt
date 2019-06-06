package API

import Models.login
import Models.user
import Models.video
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

    //Daftar user
//    @FormUrlEncoded
//    @POST("auth/login/store")
//    fun registerUser(@Field("email") email: String,
//                  @Field("password") password: String): Call<login>

    //Login user
    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(@Field("email") email: String,
                    @Field("password") password: String): Call<login>

    //show user
//    @FormUrlEncoded
    @GET("user/{id}/")
    fun showUser(@Path("id") id: Int, @Header("Authorization") authHeader: String): Call<user>


}