package com.android.apod.utils

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import java.lang.StringBuilder
import java.util.*

class DateTimeUtils{
    companion object {
        fun chooseDate(context: Context,listener : DateTimeListener) {
            val calender: Calendar = Calendar.getInstance()
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                val dialog = DatePickerDialog(
                    context,
                    object : DatePickerDialog.OnDateSetListener {
                        override fun onDateSet(
                            view: DatePicker?,
                            year: Int,
                            month: Int,
                            dayOfMonth: Int
                        ) {
                            val date: String = setDate(year, month + 1, dayOfMonth)
                            listener.chooseDate(date)
                        }

                    },
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)
                )
                dialog.show()
            }
        }

        private fun setDate(year: Int, month: Int, dayOfMonth: Int): String {
            val builder = StringBuilder("")
            builder.append(year)
            builder.append("-")
            if (month < 10) {
                builder.append("0")
            }
            builder.append(month)
            builder.append("-")
            if (dayOfMonth < 10) {
                builder.append("0")
            }
            builder.append(dayOfMonth)
            return builder.toString()
        }

        fun defaultDate(): String {
            val calendar = Calendar.getInstance()
            return setDate(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
    }
}