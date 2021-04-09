package com.android.apod.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.apod.data.api.ApodService
import com.android.apod.data.db.ApodDatabse
import com.android.apod.data.model.AstronomyPicture
import kotlinx.coroutines.*

class ApodViewModel : ViewModel() {

    private val mApod: MutableLiveData<List<AstronomyPicture>> = MutableLiveData()

    var database: ApodDatabse? = null
    fun init(startDate: String, endDate: String, database: ApodDatabse?) {
        this.database = database
        saveDataInDatabase(startDate, endDate)
    }

    fun saveDataInDatabase(startDate: String, endDate: String) {
        database?.let {
            val service = ApodService.getInstance()
            service?.getApods(startDate, endDate, it)
        }
    }

    fun getApods(startDate: String, endDate: String): LiveData<List<AstronomyPicture>> {
        GlobalScope.launch {
            database?.let {
                val data = it.apodDao().get(startDate, endDate)
                mApod.postValue(data)
            }
        }
        return mApod
    }
}