package API

import Models.category
import Models.video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VideoInterface {

    //get all video
    @GET("video/")
    fun indexVideo(@Header("Authorization") authHeader: String) : Call<ArrayList<video>>

    //get all category
    @GET("video/")
    fun indexCategory(@Header("Authorization") authHeader: String) : Call<ArrayList<category>>
}