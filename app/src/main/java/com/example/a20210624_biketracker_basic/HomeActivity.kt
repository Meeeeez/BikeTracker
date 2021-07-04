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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.tabs
import kotlinx.android.synthetic.main.activity_tours.*

val CHANNEL_ID = "channel_id_01"

class HomeActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectedTab: Int = intent.getIntExtra("tab", 0)
        tabs.getTabAt(selectedTab)?.select()

        var clickCounter: Int = 0
        button.setOnClickListener {
            if (clickCounter % 2 == 0) {
                chronometer2.base = SystemClock.elapsedRealtime()
                chronometer2.start()
                sendNotification()
                button.text = "stop"
            } else {
                chronometer2.stop()
                deleteNotification()
                button.text = "restart"
            }

            clickCounter++
        }

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (clickCounter % 2 == 0) {
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
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
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

    private fun deleteNotification(){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(0)
    }

    fun switchToTab(index: Int) {
        if (button.text.equals("stop")){

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
}