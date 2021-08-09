package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.widget.Toast

class DataHelper(private val context: Context?) {

    private val dbHelper = SQLiteDBHelper(context)
    private var tourID = arrayListOf<String>()
    private var tourStart = arrayListOf<String>()
    private var tourDestination = arrayListOf<String>()
    private var tourDate = arrayListOf<String>()
    private var tourDuration = arrayListOf<String>()

    @SuppressLint("ShowToast")
    fun saveDataInArray(showToast: Boolean) {
        val cursor: Cursor? = dbHelper.readAllData()
        if (cursor != null) {
            if (cursor.count == 0 && showToast) {
                Toast.makeText(context, "No data in DB", Toast.LENGTH_SHORT).show()
            } else {
                while (cursor.moveToNext()) {
                    tourID.add(cursor.getString(0))
                    tourStart.add(cursor.getString(1))
                    tourDestination.add(cursor.getString(2))
                    tourDate.add(cursor.getString(3))
                    tourDuration.add(cursor.getString(4))
                }
            }
        }
    }

    fun secondsToFormattedTime(totalSeconds: String): String {
        val hours = totalSeconds.toInt() / 3600
        val minutes = ((totalSeconds.toInt()) / 60) % 60
        val seconds = totalSeconds.toInt() % 60
        lateinit var formattedTime: String

        formattedTime = if (hours < 10) {
            if (hours == 0) {
                ""
            } else {
                "0$hours:"
            }
        } else {
            "$hours:"
        }

        formattedTime += if (minutes < 10) {
            "0$minutes:"
        } else {
            "$minutes:"
        }

        formattedTime += if (seconds < 10) {
            "0$seconds"
        } else {
            "$seconds"
        }

        return formattedTime
    }

    fun getAllData(): MutableMap<String, ArrayList<String>> {
        val map: MutableMap<String, ArrayList<String>> = HashMap()
        map["id"] = tourID
        map["start"] = tourStart
        map["destination"] = tourDestination
        map["date"] = tourDate
        map["duration"] = tourDuration

        return map
    }

    fun getDataByIndex(index: Int): ArrayList<String>? {
        return when (index) {
            0 -> tourID
            1 -> tourStart
            2 -> tourDestination
            3 -> tourDate
            4 -> tourDuration
            else -> null
        }
    }
}