package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor

class DataHelper(context: Context?) {

    private val dbHelper = SQLiteDBHelper(context)
    private var tourID =  arrayListOf<String>()
    private var tourStart = arrayListOf<String>()
    private var tourDestination = arrayListOf<String>()
    private var tourDate = arrayListOf<String>()
    private var tourDuration = arrayListOf<String>()

    @SuppressLint("ShowToast")
    fun saveDataInArray() {
        val cursor: Cursor? = dbHelper.readAllData()
        if (cursor != null) {
            if (cursor.count == 0) {
                println("no data in db")
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