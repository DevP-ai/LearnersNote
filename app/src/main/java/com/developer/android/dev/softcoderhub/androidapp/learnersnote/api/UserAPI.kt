package com.developer.android.dev.softcoderhub.androidapp.learnersnote.api

import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserRequest
import com.developer.android.dev.softcoderhub.androidapp.learnersnote.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    @POST("/users/signup")
    suspend fun signup(@Body userRequest: UserRequest):Response<UserResponse>

    @POST("/users/signin")
    suspend fun signIn(@Body userRequest: UserRequest):Response<UserResponse>

}