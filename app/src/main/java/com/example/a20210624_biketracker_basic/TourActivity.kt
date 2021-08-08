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

    private val dbHelper = SQLiteDBHelper(this@TourActivity)
    private var tourID =  arrayListOf<String>()
    private var tourStart = arrayListOf<String>()
    private var tourDestination = arrayListOf<String>()
    private var tourDate = arrayListOf<String>()
    private var tourDuration = arrayListOf<String>()
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

        saveDataInArray()

        customAdapter = CustomAdapter(this@TourActivity, tourID, tourStart, tourDestination, tourDuration, tourDate)
        recyclerView.adapter = customAdapter
        recyclerView.layoutManager = LinearLayoutManager(this@TourActivity)
    }

    @SuppressLint("ShowToast")
    private fun saveDataInArray() {
        val cursor: Cursor? = dbHelper.readAllData()
        if (cursor != null) {
            if (cursor.count == 0) {
                Toast.makeText(this@TourActivity, "No Tours saved", Toast.LENGTH_SHORT).show()
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