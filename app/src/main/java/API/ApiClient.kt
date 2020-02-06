package API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient{
    private val BASE_URL: String = "https://api.fingeringpiano.com/"

    fun getThumbnail(): String {
        return BASE_URL + "images/thumbnails/"
    }

    fun getApiClient(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

//    fun mIntercepter(): OkHttpClient {
//        val okHttpClient = OkHttpClient.Builder()
//
//    }
}