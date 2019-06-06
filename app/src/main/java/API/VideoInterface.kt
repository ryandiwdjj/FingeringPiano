package API

import Models.video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VideoInterface {

    //get all video
    @GET("video/")
    fun indexVideo(@Header("Authorization") authHeader: String) : Call<List<video>>
}