package com.android.apod.view.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.android.apod.R
import com.android.apod.data.model.AstronomyPicture
import com.bumptech.glide.Glide

class ApodItemFragment(context: Context, listener: OnApodItemClickListener) : Fragment() {
    private val listener = listener
    private val mContext = context
    private lateinit var clearBtn: ImageView
    private lateinit var fragmentItemTitle: TextView
    private lateinit var fragmentItemImageUrl: ImageView
    private lateinit var fragmentItemExplanation: TextView
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

        }
        return view
    }

    open fun setItem(item: AstronomyPicture) {
        fragmentItemTitle.text = item.title
        fragmentItemExplanation.text = item.explanation
        Glide.with(mContext).load(item.url).into(fragmentItemImageUrl)
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