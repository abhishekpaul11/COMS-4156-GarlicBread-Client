package com.garlicbread.includify.retrofit

import com.garlicbread.includify.models.request.LoginRequest
import com.garlicbread.includify.models.response.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("registration/login")
    fun login(@Body userCredentials: LoginRequest): Call<String>

    @GET("registration")
    fun fetchUserDetails(@Header("Authorization") accessToken: String): Call<User>
}
