package com.android.apod.view.ui

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import com.android.apod.R
import com.android.apod.utils.ApodFileUtils
import com.android.apod.utils.DateTimeListener
import com.android.apod.utils.DateTimeUtils
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var submitBtn: Button
    private lateinit var start_date_picker: TextView
    private lateinit var end_date_picker: TextView
    private lateinit var todayDate: String
    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        submitBtn = findViewById(R.id.show_Apod_btn)
        start_date_picker = findViewById(R.id.start_date_picker)
        end_date_picker = findViewById(R.id.end_date_picker)

        submitBtn.setOnClickListener(this)
        start_date_picker.setOnClickListener(this)
        end_date_picker.setOnClickListener(this)

        todayDate = DateTimeUtils.defaultDate()
    }

    override fun onClick(v: View?) {
        when (v) {
            submitBtn -> {
                onValidation()
            }
            start_date_picker -> {
                chooseDate(start_date_picker)
            }
            end_date_picker -> {
                chooseDate(end_date_picker)
            }
        }
    }

    private fun onValidation() {
        startDate = start_date_picker.text as String
        endDate = end_date_picker.text as String
        val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_title)
            .setMessage(R.string.alert_dialog_message)
            .setPositiveButton("Proceed", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    val intent = Intent(this@MainActivity, ApodActivity::class.java)
                    intent.putExtra(
                        "start_date",
                        if (startDate.equals("Start Date")) todayDate else startDate
                    )
                    intent.putExtra(
                        "end_date",
                        if (endDate.equals("End Date")) todayDate else endDate
                    )
                    startActivity(intent)
                }
            }).setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    dialog?.let {
                        it.dismiss()
                    }
                }
            })
        dialog.create().show()
    }

    fun chooseDate(textView: TextView) {
        DateTimeUtils.chooseDate(this, object : DateTimeListener {
            override fun chooseDate(date: String) {
                textView.text = date
            }
        })
    }
}