package com.android.apod.view.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.android.apod.BuildConfig
import com.android.apod.R
import com.android.apod.data.model.AstronomyPicture
import com.android.apod.utils.ApodFileUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class ApodItemFragment(context: Context, listener: OnApodItemClickListener) : Fragment() {
    private val listener = listener
    private val mContext = context
    private lateinit var clearBtn: ImageView
    private lateinit var fragmentItemTitle: TextView
    private lateinit var fragmentItemImageUrl: ImageView
    private lateinit var fragmentItemExplanation: TextView
    private var currentItem: AstronomyPicture? = null
    private lateinit var saveItem: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_apod_item, container, false)

        clearBtn = view.findViewById(R.id.clearFragment)
        fragmentItemTitle = view.findViewById(R.id.fragmentItemTitle)
        fragmentItemImageUrl = view.findViewById(R.id.fragemntItemImage)
        fragmentItemExplanation = view.findViewById(R.id.fragmentItemExplanation)
        saveItem = view.findViewById(R.id.saveItem)

        clearBtn.setOnClickListener {
            listener.onItemClose()
        }
        saveItem.setOnClickListener {
            saveImageAndData()
        }
        return view
    }

    private fun saveImageAndData() {
        val directory = ApodFileUtils.getDirectory()
        currentItem?.let {
            val currentFile = File(directory, "tempFile.jpg")
            if (ActivityCompat.checkSelfPermission(
                    mContext,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                checkPermissions()
            } else {
                currentFile.parentFile.mkdir()
                if(currentFile.exists() == false) {
                    currentFile.createNewFile()
                }
                try {
                    val fos = FileOutputStream(currentFile)
                    GlobalScope.launch {
                        try {
                            val bitmap = Picasso.with(context).load(it.url).get()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)


                            //sharing data
                            val sharingIntent = Intent(Intent.ACTION_SEND)
                            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                            sharingIntent.type = "image/*"

                            val uri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID+".provider", currentFile)
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri)
                            startActivity(Intent.createChooser(sharingIntent, "Share via"))
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun checkPermissions() {
        val perms = arrayOf(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val paramscode = 200
        requestPermissions(perms,paramscode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 200) {
            if (PackageManager.PERMISSION_DENIED == grantResults[0] || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                listener.onItemClose()
            }
        } else {
            Toast.makeText(mContext, "Permission Denied", Toast.LENGTH_LONG).show()
        }
    }

    open fun setItem(item: AstronomyPicture) {
        currentItem = item
        fragmentItemTitle.text = item.title
        fragmentItemExplanation.text = item.explanation
        if (item.media_type.equals("image")) {
            Picasso.with(mContext).load(item.url).into(fragmentItemImageUrl)
        } else {
            val builder = StringBuilder("Image is not available for : ")
            builder.append(item.date)
            Toast.makeText(context, builder.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    companion object {
        private var instance: ApodItemFragment? = null

        @JvmStatic
        fun newInstance(context: Context, listener: OnApodItemClickListener): ApodItemFragment {
            if (instance == null) {
                instance = ApodItemFragment(context, listener)
            }
            return instance!!
        }
    }
}