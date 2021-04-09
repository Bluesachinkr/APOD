package com.android.apod.view.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.apod.data.api.ApodService
import com.android.apod.data.db.ApodDatabse
import com.android.apod.data.model.AstronomyPicture
import kotlinx.coroutines.*

class ApodViewModel : ViewModel() {

    private var context: Context? = null
    lateinit var mApod: MutableLiveData<List<AstronomyPicture>>
    var database: ApodDatabse? = null
    fun init(context: Context, startDate: String, endDate: String) {
        this.context = context
        mApod = MutableLiveData()
        database = ApodDatabse.getInstance(context)
        saveDataInDatabase(startDate, endDate)
    }

    // comment: don't pass context to a ViewModel
    // you can pass service an database instead from activity

    // comment:
    // ViewModels should have only business logic. it should n't have ui elements or context of the activity
    // the viewmodel reference should be there in activity, viewmodel should not have any reference to acvitity
    // you tell me why we do like this ?

    fun saveDataInDatabase(startDate: String, endDate: String) {
        database?.let {
            val service = context?.let { it1 -> ApodService(it1) }
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