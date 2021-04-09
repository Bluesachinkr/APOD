package com.android.apod.data.db

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.android.apod.data.model.AstronomyPicture

@Dao
interface ApodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: List<AstronomyPicture>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: AstronomyPicture)

    @Query("SELECT * FROM AstronomyPicture WHERE  date BETWEEN (:startDate) and (:endDate)")
    fun get(startDate: String, endDate: String): List<AstronomyPicture>
}