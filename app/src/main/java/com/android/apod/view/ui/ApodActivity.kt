package com.android.apod.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.apod.R
import com.android.apod.data.db.ApodDatabse
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
    private var isItemOpen: Boolean = false
    private var database: ApodDatabse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apod)

        val intent = intent
        intent?.let {
            start_date = intent.getStringExtra("start_date") as String
            end_date = intent.getStringExtra("end_date") as String
        }

        supportActionBar?.let {
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
        }

        this.apodRecycler = findViewById(R.id.itemRecyclerView)
        this.apodItemContainer = findViewById(R.id.itemContainer)
        mApodList = mutableListOf()
        loadViewModel()

        this.fragment = ApodItemFragment.newInstance(this, this as OnApodItemClickListener)
        fragmentManager.beginTransaction().add(R.id.itemContainer, fragment).commit()
        intitializeRecycler()
    }

    private fun loadViewModel() {
        //databse instance
        database = ApodDatabse.getInstance(this)

        this.mApodViewModel = ViewModelProviders.of(this).get(ApodViewModel::class.java)
        this.mApodViewModel.init(start_date, end_date, database)
        this.mApodViewModel.getApods(start_date, end_date)
            .observe(this, object : Observer<List<AstronomyPicture>> {
                override fun onChanged(t: List<AstronomyPicture>?) {
                    t?.let {
                        mApodList.addAll(it)
                    }
                    apodAdapter.notifyDataSetChanged()
                }
            })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
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
        isItemOpen = true
        fragment.setItem(item)
    }

    override fun onItemClose() {
        this.apodRecycler.visibility = View.VISIBLE
        this.apodItemContainer.visibility = View.GONE
        isItemOpen = false
    }

    override fun onBackPressed() {
        if (isItemOpen) {
            fragmentManager.popBackStack()
            onItemClose()
        } else {
            super.onBackPressed()
        }
    }
}