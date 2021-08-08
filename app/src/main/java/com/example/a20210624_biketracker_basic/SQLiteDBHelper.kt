package com.example.a20210624_biketracker_basic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteDBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var result = 0L

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //Tour
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TOUR_TABLE_NAME + " (" +
                    TOUR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TOUR_START + " VARCHAR(100), " +
                    TOUR_DESTINATION + " VARCHAR(100), " +
                    TOUR_DATE + " VARCHAR(10), " +
                    TOUR_DURATION + " INTEGER UNSIGNED" + ")"           //seconds
        )
        //Maintenance
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + MAINTENANCE_TABLE_NAME + " (" +
                    MAINTENANCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MAINTENANCE_NAME + " VARCHAR(50), " +
                    MAINTENANCE_INTERVAL + " INTEGER UNSIGNED" + ")"    //hours
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TOUR_TABLE_NAME")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $MAINTENANCE_TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    fun addTour(duration: String?, start: String?, destination: String?, date: String?) { // string for now watch for datatype that allows adding times
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(TOUR_DURATION, duration)
        cv.put(TOUR_START, start)
        cv.put(TOUR_DESTINATION, destination)
        cv.put(TOUR_DATE, date)
        result = db.insert(TOUR_TABLE_NAME, null, cv)

        println(duration)
        println(start)
        println(destination)
    }

    fun timeToSeconds(time: String): String {
        val splittedTimeString: ArrayList<String> = time.split(":") as ArrayList<String>
        val splittedTimeInt = splittedTimeString.map { it.toInt() } as ArrayList<Int>

        if (splittedTimeInt.size < 3){     //check if format is 00:00:00 or 00:00
            splittedTimeInt.add(0, 0)
        }

        return ((splittedTimeInt[0] * 60 + splittedTimeInt[1]) * 60 + splittedTimeInt[2]).toString()
    }

    fun readAllData(): Cursor? {
        val query = "SELECT * FROM $TOUR_TABLE_NAME"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    companion object {
        private const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "BikeTrackerDB"

        private const val TOUR_TABLE_NAME = "TTour"
        private const val TOUR_ID = "tourID"
        private const val TOUR_START = "start"
        private const val TOUR_DESTINATION = "destination"
        private const val TOUR_DATE = "date"
        private const val TOUR_DURATION = "duration"

        private const val MAINTENANCE_TABLE_NAME = "TMaintenance"
        private const val MAINTENANCE_ID = "maintenanceID"
        private const val MAINTENANCE_NAME = "name"
        private const val MAINTENANCE_INTERVAL = "interval"
    }
}