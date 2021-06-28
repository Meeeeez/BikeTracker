package com.example.a20210624_biketracker_basic

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class ServiceActivity: AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val selectedTab: Int = intent.getIntExtra("tab", 0)
        tabs.getTabAt(selectedTab)?.select()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab) {
                    tabs.getTabAt(0) -> switchToTab(0)
                    tabs.getTabAt(1) -> switchToTab(1)
                    tabs.getTabAt(2) -> switchToTab(2)
                    else -> {
                        print("error")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun switchToTab(index: Int) {
        if (index == 0) {
            val intent = Intent(this@ServiceActivity, HomeActivity::class.java)
            intent.putExtra("tab", 0)
            startActivity(intent)
        } else if (index == 1) {
            val intent = Intent(this@ServiceActivity, TourActivity::class.java)
            intent.putExtra("tab", 1)
            startActivity(intent)
        }
    }
}