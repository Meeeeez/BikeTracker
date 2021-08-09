package com.example.a20210624_biketracker_basic
import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.tabs
import kotlinx.android.synthetic.main.activity_tours.*

class TourActivity: AppCompatActivity() {

    private lateinit var customAdapter: CustomAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tours)

        val selectedTab: Int = intent.getIntExtra("tab", 0)
        tabs.getTabAt(selectedTab)?.select()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab) {
                    tabs.getTabAt(0) -> switchToTab(0)
                    tabs.getTabAt(1) -> switchToTab(1)
                    tabs.getTabAt(2) -> switchToTab(2)
                    else -> {
                        Toast.makeText(this@TourActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        val dataHelper = DataHelper(this@TourActivity)
        val dataMap: MutableMap<String, ArrayList<String>>? = dataHelper.saveDataInArray()

        customAdapter = CustomAdapter(this@TourActivity, dataMap)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@TourActivity)
    }

    fun switchToTab(index: Int) {
        if (index == 0) {
            val intent = Intent(this@TourActivity, HomeActivity::class.java)
            intent.putExtra("tab", 0)
            startActivity(intent)
        } else if (index == 2) {
            val intent = Intent(this@TourActivity, ServiceActivity::class.java)
            intent.putExtra("tab", 2)
            startActivity(intent)
        }
    }
}