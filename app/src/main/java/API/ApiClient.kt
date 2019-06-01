package API

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient (){
    private val BASE_URL: String = "http://192.168.1.120:8000/"

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