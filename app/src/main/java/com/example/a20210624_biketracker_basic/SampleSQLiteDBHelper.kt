package com.example.a20210624_biketracker_basic

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SampleSQLiteDBHelper(context: Context?) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //Tour
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + TOUR_TABLE_NAME + " (" +
                    TOUR_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
                    TOUR_START + " VARCHAR(100), " +
                    TOUR_DESTINATION + " VARCHAR(100), " +
                    TOUR_DURATION + " INT UNSIGNED" + ")"           //minutes
        )
        //Maintenance
        sqLiteDatabase.execSQL(
            "CREATE TABLE " + MAINTENANCE_TABLE_NAME + " (" +
                    MAINTENANCE_ID + " INT PRIMARY KEY AUTOINCREMENT, " +
                    MAINTENANCE_NAME + " VARCHAR(50), " +
                    MAINTENANCE_INTERVAL + " INT UNSIGNED" + ")"    //hours
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $TOUR_TABLE_NAME")
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS $MAINTENANCE_TABLE_NAME")
        onCreate(sqLiteDatabase)
    }

    companion object {
        private const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "BikeTrackerDB"

        private const val TOUR_TABLE_NAME = "TTour"
        private const val TOUR_ID = "tourID"
        private const val TOUR_START = "start"
        private const val TOUR_DESTINATION = "destination"
        private const val TOUR_DURATION = "duration"

        private const val MAINTENANCE_TABLE_NAME = "TMaintenance"
        private const val MAINTENANCE_ID = "maintenanceID"
        private const val MAINTENANCE_NAME = "name"
        private const val MAINTENANCE_INTERVAL = "interval"
    }
}