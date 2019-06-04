package API

import Models.login
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import Models.user
import retrofit2.Call
import retrofit2.http.Path

interface UserInterface {

    //Login user
    @FormUrlEncoded
    @POST("auth/login")
    fun loginUser(@Field("email") email: String,
                    @Field("password") password: String): Call<login>

}