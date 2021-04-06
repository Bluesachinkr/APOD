package com.android.apod.view.viewmodel

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.apod.data.api.ApodService
import com.android.apod.data.db.ApodDatabse
import com.android.apod.data.model.AstronomyPicture
import kotlinx.coroutines.*

class ApodViewModel : ViewModel() {

    lateinit var mApod: MutableLiveData<List<AstronomyPicture>>
    var database: ApodDatabse? = null
    fun init(context: Context, startDate: String, endDate: String) {
        mApod = MutableLiveData()
        database = ApodDatabse.getInstance(context)
        saveDataInDatabase(startDate, endDate)
    }

    fun saveDataInDatabase(startDate: String, endDate: String) {
        database?.let {
            val service = ApodService()
            service.getApods(startDate, endDate, it)
        }
    }

    fun getApods(startDate: String, endDate: String): LiveData<List<AstronomyPicture>> {
        GlobalScope.launch {
            database?.let {
               /* val query = SimpleSQLiteQuery(
                    "SELECT * FROM AstronomyPicture WHERE  date BETWEEN ? and ?",
                    arrayOf(startDate, endDate)
                )*/
                val data = it.apodDao().get(startDate,endDate)
                mApod.postValue(data)
            }
        }
        return mApod
    }
}