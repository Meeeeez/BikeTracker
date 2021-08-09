package com.example.a20210624_biketracker_basic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter internal constructor(
    private val context: Context,
    private val dataMap: MutableMap<String, ArrayList<String>>?

) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.tour_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.tourIDTxt.text = dataMap?.get("id")?.get(position).toString()
        holder.tourStartTxt.text = dataMap?.get("start")?.get(position).toString()
        holder.tourDestinationTxt.text = dataMap?.get("destination")?.get(position).toString()
        holder.tourDateTxt.text = dataMap?.get("date")?.get(position).toString()
        holder.tourDurationTxt.text = holder.dataHelper.secondsToFormattedTime(
            dataMap?.get("duration")?.get(position).toString()
        )
    }

    override fun getItemCount(): Int {
        val data: ArrayList<String>? = dataMap?.get("id")

        if (data != null) {
            return data.size
        }

        return -1
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tourIDTxt: TextView = itemView.findViewById(R.id.tourIDText)
        var tourStartTxt: TextView = itemView.findViewById(R.id.tourStartText)
        var tourDestinationTxt: TextView = itemView.findViewById(R.id.tourDestinationText)
        var tourDurationTxt: TextView = itemView.findViewById(R.id.tourDurationText)
        var tourDateTxt: TextView = itemView.findViewById(R.id.tourDateText)
        val dataHelper = DataHelper(context)
    }
}