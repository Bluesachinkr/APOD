package com.android.apod.data.api

import com.android.apod.data.model.AstronomyPicture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApodApiAService {

    @GET("apod")
    fun getUsers(
       @QueryMap map : HashMap<String,String>
    ): Call<List<AstronomyPicture>>
}