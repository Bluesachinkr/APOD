package com.android.apod.view.ui

import com.android.apod.data.model.AstronomyPicture

interface OnApodItemClickListener {
    fun onItemOpen(item : AstronomyPicture)
    fun onItemClose()
}