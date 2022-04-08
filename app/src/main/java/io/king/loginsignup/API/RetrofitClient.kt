package io.king.loginsignup.API

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {
    // Login Request
    @Headers("Accept:application/json","Content-Type:application/json")
    // @Post calls end point
    @POST("login")
    fun signin(@Body info: SignInBody): retrofit2.Call<ResponseBody>

    // Signup request
    @Headers("Accept:application/json","Content-Type:application/json")
    // @Post calls end point
    @POST("card")
    fun registerUser(
        @Body info: UserBody
    ): retrofit2.Call<ResponseBody>
}
class   RetrofitInstance {
    companion object {
        val BASE_URL: String = "https://save-life-project.herokuapp.com/api/"

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}