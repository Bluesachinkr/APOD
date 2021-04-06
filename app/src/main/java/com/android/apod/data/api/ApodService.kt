package com.android.apod.data.api

import com.android.apod.data.db.ApodDatabse
import com.android.apod.data.model.AstronomyPicture
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApodService {
    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.nasa.gov/planetary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    open fun getApods(
        start_date: String,
        end_date: String, database: ApodDatabse
    ) {
        val service: ApodApiAService = retrofit.create(ApodApiAService::class.java)
        val map = hashMapOf<String, String>()
        map.put("end_date", end_date)
        map.put("start_date", start_date)
        map.put("api_key", "nYmckftOiQxdiDnFTQkABTS29gwwLLxubDlNGQpZ")
        val call: Call<List<AstronomyPicture>> = service.getUsers(map)
        call.enqueue(object : Callback<List<AstronomyPicture>> {
            override fun onResponse(
                call: Call<List<AstronomyPicture>>,
                response: Response<List<AstronomyPicture>>
            ) {
                val pictures = response.body()
                pictures?.let {
                    GlobalScope.launch {
                        database.apodDao().insert(it)
                    }
                }
            }

            override fun onFailure(call: Call<List<AstronomyPicture>>, t: Throwable) {
            }
        })
    }
}