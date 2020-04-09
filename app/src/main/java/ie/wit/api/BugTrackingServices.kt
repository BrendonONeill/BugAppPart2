package ie.wit.api

import com.google.gson.GsonBuilder
import ie.wit.models.BugTrackingModel

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface BugTrackingService {
    @GET("/bugTrackings")
    fun getall(): Call<List<BugTrackingModel>>

    @GET("/bugTrackings/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<BugTrackingModel>>

    @GET("/bugTrackings/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<BugTrackingModel>

    @DELETE("/bugTrackings/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<BugTrackingWrapper>

    @POST("/bugTrackings/{email}")
    fun post(@Path("email") email: String?,
             @Body donation: BugTrackingModel)
            : Call<BugTrackingWrapper>

    @PUT("/bugTrackings/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body donation: BugTrackingModel
    ): Call<BugTrackingWrapper>

    companion object {

        val serviceURL = "https://donationweb-hdip-mu-server.herokuapp.com"

        fun create() : BugTrackingService {

            val gson = GsonBuilder().create()

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(BugTrackingService::class.java)
        }
    }
}