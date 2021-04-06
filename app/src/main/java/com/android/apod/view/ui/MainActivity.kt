package com.android.apod.view.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.TextView
import com.android.apod.R
import java.lang.StringBuilder
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var submitBtn: Button
    private lateinit var start_date_picker: TextView
    private lateinit var end_date_picker: TextView
    private lateinit var date_picker: DatePicker
    private lateinit var buttonsLayout: LinearLayout
    private lateinit var todayDate: String
    private var startDate: String = ""
    private var endDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitBtn = findViewById(R.id.show_Apod_btn)
        start_date_picker = findViewById(R.id.start_date_picker)
        end_date_picker = findViewById(R.id.end_date_picker)

        submitBtn.setOnClickListener(this)
        start_date_picker.setOnClickListener(this)
        end_date_picker.setOnClickListener(this)

        todayDate = defaultDate()
    }

    override fun onClick(v: View?) {
        when (v) {
            submitBtn -> {
                startDate = start_date_picker.text as String
                endDate = end_date_picker.text as String
                val intent = Intent(this, ApodActivity::class.java)
                intent.putExtra(
                    "start_date",
                    if (startDate.equals("Start Date")) todayDate else startDate
                )
                intent.putExtra("end_date", if (endDate.equals("End Date")) todayDate else endDate)
                startActivity(intent)
                finish()
            }
            start_date_picker -> {
                chooseDate(start_date_picker)
            }
            end_date_picker -> {
                chooseDate(end_date_picker)
            }
        }
    }

    private fun chooseDate(textView: TextView) {
        var date: MutableList<Int> = mutableListOf()
        val calender: Calendar = Calendar.getInstance()
        date.add(0, calender.get(Calendar.DAY_OF_MONTH))
        date.add(1, calender.get(Calendar.MONTH))
        date.add(2, calender.get(Calendar.YEAR))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            val dialog = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    date.set(2, year)
                    date.set(1, month)
                    date.set(0, dayOfMonth)
                    val builder: StringBuilder = StringBuilder("")
                    builder.append(year)
                    builder.append("-")
                    if (month < 9) {
                        builder.append("0")
                    }
                    builder.append(month + 1)
                    builder.append("-")
                    if (dayOfMonth < 10) {
                        builder.append("0")
                    }
                    builder.append(dayOfMonth)
                    val date: String = builder.toString()
                    textView.text = date
                }

            }, date[2], date[1], date[1])
            dialog.show()
        }
    }

    private fun defaultDate(): String {
        val calendar = Calendar.getInstance()
        val builder = StringBuilder("")
        builder.append(calendar.get(Calendar.YEAR))
        builder.append("-")
        builder.append(calendar.get(Calendar.MONTH))
        builder.append("-")
        builder.append(calendar.get(Calendar.DAY_OF_MONTH))
        return builder.toString()
    }
}