package com.fingering.fingeringpiano.API

import com.fingering.fingeringpiano.Models.category
import com.fingering.fingeringpiano.Models.video
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface VideoInterface {

    //get all video
    @GET("video/")
    fun indexVideo(@Header("Authorization") authHeader: String) : Call<ArrayList<video>>

    //get all category
    @GET("category/")
    fun indexCategory(@Header("Authorization") authHeader: String) : Call<ArrayList<category>>
}