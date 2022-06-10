package com.example.nguser.data.remote

import com.example.nguser.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {

    @GET("021003eeb1125afe17ec")
    suspend fun getUsers(): Response<UserResponse>

}