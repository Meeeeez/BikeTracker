package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tabs
import kotlinx.android.synthetic.main.activity_tours.*

/*
    TODO: - create new small icon fro notification
          - save tour
          - fix bug when starting timer (system.elapsedTime)
 */

val CHANNEL_ID = "channel_id_01"

class HomeActivity : AppCompatActivity() {

    private var originButton = IntArray(2)
    private var originChronometer = IntArray(2)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectedTab: Int = intent.getIntExtra("tab", 0)
        tabs.getTabAt(selectedTab)?.select()

        button.setOnClickListener {

            showInputsFields()

            if (!button.text.equals("stop")) {
                chronometer2.start()
                sendNotification()
                TabLayoutUtils.enableTabs(tabs, false)
                button.text = "stop"
                inputStart.text.clear()
                inputDestination.text.clear()
            } else if (button.text == "stop" && inputStart.text.isNotBlank() && inputDestination.text.isNotBlank()) {
                chronometer2.stop()
                deleteNotification()
                TabLayoutUtils.enableTabs(tabs, true)

                hideInputFields()

                button.text = "start"

                // save tour here

                Toast.makeText(
                    this@HomeActivity,
                    "Saved Tour",
                    Toast.LENGTH_SHORT
                ).show()

                chronometer2.base = SystemClock.elapsedRealtime()
            } else {
                Toast.makeText(
                    this@HomeActivity,
                    "Enter Start and Destination",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (!button.text.equals("stop")) {
                    when (tab) {
                        tabs.getTabAt(0) -> switchToTab(0)
                        tabs.getTabAt(1) -> switchToTab(1)
                        tabs.getTabAt(2) -> switchToTab(2)
                        else -> {
                            print("error")
                        }
                    }
                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "Can't switch tab while timer is running",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        chronometer2.addTextChangedListener(
            object : TextWatcher {
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    sendNotification()
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

    private fun sendNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("Active Tour")
            .setContentText(chronometer2.text)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(chronometer2.text)
            )
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setOngoing(true)
            .setShowWhen(false)
            .setOnlyAlertOnce(true)
            .setSound(null)

        with(NotificationManagerCompat.from(this)) {
            notify(0, builder.build())
        }
    }

    private fun deleteNotification() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(0)
    }

    fun switchToTab(index: Int) {
        if (button.text.equals("stop")) {

            val tabStrip = tabs.getChildAt(0) as LinearLayout
            tabStrip.isEnabled = false
            for (i in 0 until tabStrip.childCount) {
                tabStrip.getChildAt(i).isClickable = false
            }

            Toast.makeText(
                this@HomeActivity,
                "can't switch while timer is running ",
                Toast.LENGTH_SHORT
            ).show()
        } else if (index == 1) {
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
        button.getLocationOnScreen(originButton)
        chronometer2.getLocationOnScreen(originChronometer)

        chronometer2.animate()
            .y(1500f)
        inputDestination.isVisible = true
        inputStart.isVisible = true
        button.animate()
            .y(2150f)
            .scaleXBy(0.03f)
            .scaleYBy(0.03f)
            .withEndAction {
                button.animate()
                    .scaleXBy(-0.03f)
                    .scaleYBy(-0.03f)
            }
    }

    private fun hideInputFields() {
        chronometer2.animate()
            .y(1580f)
        inputDestination.isVisible = false
        inputStart.isVisible = false
        button.animate()
            .y(2015f)
    }
}