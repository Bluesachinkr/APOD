package com.android.apod.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.apod.data.model.AstronomyPicture

@Database(entities = [AstronomyPicture::class], version = 1, exportSchema = false)
abstract class ApodDatabse : RoomDatabase() {

    abstract fun apodDao(): ApodDao

    companion object {

        // comment: make 'instance: ApodDatabse?' a singleton, if don't know what is singleton, read about it

        // comment: also explain what is the use of the keyword - synchronized and why is it used here

        private var instance: ApodDatabse? = null

        fun getInstance(context: Context): ApodDatabse? {
            if (instance == null) {
                synchronized(ApodDatabse::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApodDatabse::class.java,
                        "AstronomyPicture"
                    )
                        .build()
                }
            }
            return instance
        }
    }
}