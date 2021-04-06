package com.android.apod.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class AstronomyPicture(
    @SerializedName("media_type")
    @ColumnInfo(name = "media_type")
    val media_type: String = "",
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String = "",
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String = "",
    @ColumnInfo(name = "explanation")
    @SerializedName("explanation")
    val explanation: String = "",
    @SerializedName("date")
    @PrimaryKey
    val date: String = ""
)
