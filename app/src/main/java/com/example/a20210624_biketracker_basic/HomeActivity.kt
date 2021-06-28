package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.android.synthetic.main.activity_main.*


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
                chronometer2.start()
                button.text = "stop"
            } else {
                chronometer2.stop()
                button.text = "start"
            }

            clickCounter++
        }

        tabs.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if(clickCounter % 2 == 0){
                    when (tab) {
                        tabs.getTabAt(0) -> switchToTab(0)
                        tabs.getTabAt(1) -> switchToTab(1)
                        tabs.getTabAt(2) -> switchToTab(2)
                        else -> {
                            print("error")
                        }
                    }
                }else {
                    Toast.makeText(this@HomeActivity, "Can't switch tab while timer is running", Toast.LENGTH_LONG).show()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun switchToTab(index: Int) {
        if (button.text.equals("stop")){

            val tabStrip = tabs.getChildAt(0) as LinearLayout
            tabStrip.isEnabled = false
            for (i in 0 until tabStrip.childCount) {
                tabStrip.getChildAt(i).isClickable = false
            }

            Toast.makeText(this@HomeActivity, "can't switch while timer is running ", Toast.LENGTH_SHORT).show()
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