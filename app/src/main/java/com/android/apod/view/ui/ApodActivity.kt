
package com.android.apod.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.apod.R
import com.android.apod.data.model.AstronomyPicture
import com.android.apod.view.adapter.ApodAdapter
import com.android.apod.view.viewmodel.ApodViewModel

class ApodActivity : AppCompatActivity(), OnApodItemClickListener {
    private lateinit var apodRecycler: RecyclerView
    private lateinit var apodItemContainer: FrameLayout
    private lateinit var apodAdapter: ApodAdapter

    private lateinit var mApodViewModel: ApodViewModel
    private lateinit var fragment: ApodItemFragment

    private lateinit var start_date: String
    private lateinit var end_date: String

    private lateinit var mApodList: MutableList<AstronomyPicture>

    private val fragmentManager: FragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apod)

        val intent = intent
        intent?.let {
            start_date = intent.getStringExtra("start_date") as String
            end_date = intent.getStringExtra("end_date") as String
        }
        this.apodRecycler = findViewById(R.id.itemRecyclerView)
        this.apodItemContainer = findViewById(R.id.itemContainer)
        mApodList = mutableListOf()
        this.mApodViewModel = ViewModelProviders.of(this).get(ApodViewModel::class.java)
        this.mApodViewModel.init(this, start_date, end_date)
        this.mApodViewModel.getApods(start_date, end_date)
            .observe(this, object : Observer<List<AstronomyPicture>> {
                override fun onChanged(t: List<AstronomyPicture>?) {
                    t?.let {
                        mApodList.addAll(it)
                    }
                    apodAdapter.notifyDataSetChanged()
                }
            })

        this.fragment = ApodItemFragment.newInstance(this, this as OnApodItemClickListener)
        fragmentManager.beginTransaction().add(R.id.itemContainer, fragment).commit()
        intitializeRecycler()
    }

    private fun intitializeRecycler() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        apodRecycler.layoutManager = layoutManager
        apodAdapter = ApodAdapter(this, mApodList, this)
        apodRecycler.adapter = apodAdapter
    }

    override fun onItemOpen(item: AstronomyPicture) {
        this.apodRecycler.visibility = View.GONE
        this.apodItemContainer.visibility = View.VISIBLE
        fragment.setItem(item)
    }

    override fun onItemClose() {
        this.apodRecycler.visibility = View.VISIBLE
        this.apodItemContainer.visibility = View.GONE
    }
}