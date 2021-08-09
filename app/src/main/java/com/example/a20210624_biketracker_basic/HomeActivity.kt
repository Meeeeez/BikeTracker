package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

/*
    TODO: - implement notification logic
          - create app icon
 */

class HomeActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectedTab: Int = intent.getIntExtra("tab", 0)
        tabs.getTabAt(selectedTab)?.select()

        initTTChronometer()

        startTimerButton.setOnClickListener {
            showInputsFields()

            if (startTimerButton.text != "stop") {
                newTourChronometer.base = SystemClock.elapsedRealtime()
                newTourChronometer.start()
                TabLayoutUtils.enableTabs(tabs, false)
                startTimerButton.text = "stop"
                inputStart.text.clear()
                inputDestination.text.clear()
            } else if (startTimerButton.text == "stop" && inputStart.text.isNotBlank() && inputDestination.text.isNotBlank()) {
                newTourChronometer.stop()
                TabLayoutUtils.enableTabs(tabs, true)

                hideInputFields()

                startTimerButton.text = "restart"

                //save tour
                val dbHelper = SQLiteDBHelper(this@HomeActivity)
                val duration = dbHelper.timeToSeconds(newTourChronometer.text.toString())
                val sdf = SimpleDateFormat("dd.M.yyyy")
                val currentDate = sdf.format(Date())
                dbHelper.addTour(duration, inputStart.text.toString(), inputDestination.text.toString(), currentDate)

                if (dbHelper.result == -1L) {
                    Toast.makeText(this@HomeActivity, "Failed to save Tour", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HomeActivity, "Saved Tour!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@HomeActivity, "Enter Start and Destination", Toast.LENGTH_SHORT).show()
            }
        }

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            @SuppressLint("ShowToast")
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(startTimerButton.text != "stop"){
                    when (tab) {
                        tabs.getTabAt(0) -> switchToTab(0)
                        tabs.getTabAt(1) -> switchToTab(1)
                        tabs.getTabAt(2) -> switchToTab(2)
                        else -> {
                            Toast.makeText(this@HomeActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        newTourChronometer.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    //update notification here
                }

                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }
                override fun afterTextChanged(s: Editable) {}
            }
        )
    }

    private fun initTTChronometer() {
        val dataHelper = DataHelper(this@HomeActivity)
        dataHelper.saveDataInArray(false)
        val duration: ArrayList<String>? = dataHelper.getDataByIndex(4)
        var sumSeconds = 0
        lateinit var formattedTime: String

        if (duration != null) {
            for (number in duration) {
                sumSeconds += number.toInt()
            }

            formattedTime = dataHelper.secondsToFormattedTime(sumSeconds.toString())
            val time = formattedTime.split(":").toTypedArray()
            if (time.size == 2) {
                totalTimeChronometer.base = (SystemClock.elapsedRealtime() - (time[0].toInt() * 60000 + time[1].toInt() * 1000))
            }else {
                totalTimeChronometer.base = (SystemClock.elapsedRealtime() - (time[0].toInt() * 3600000 + time[1].toInt() * 60000 + time[2].toInt() * 1000))
            }
        }
    }

    private fun switchToTab(index: Int) {
        if (index == 1) {
            val intent = Intent(this@HomeActivity, TourActivity::class.java)
            intent.putExtra("tab", 1)
            startActivity(intent)
        } else if (index == 2) {
            val intent = Intent(this@HomeActivity, ServiceActivity::class.java)
            intent.putExtra("tab", 2)
            startActivity(intent)
        }
    }

    private fun showInputsFields() {
        newTourChronometer.animate()
            .y(1500f)
        inputDestination.isVisible = true
        inputStart.isVisible = true
        startTimerButton.animate()
            .y(2150f)
            .scaleXBy(0.03f)
            .scaleYBy(0.03f)
            .withEndAction {
                startTimerButton.animate()
                    .scaleXBy(-0.03f)
                    .scaleYBy(-0.03f)
            }
    }

    private fun hideInputFields() {
        newTourChronometer.animate()
            .y(1580f)
        inputDestination.isVisible = false
        inputStart.isVisible = false
        startTimerButton.animate()
            .y(2015f)
    }
}