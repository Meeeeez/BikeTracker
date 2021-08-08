package com.example.a20210624_biketracker_basic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList

class CustomAdapter internal constructor(
    private val context: Context,
    private val tour_id: ArrayList<*>,
    private val tour_start: ArrayList<*>,
    private val tour_destination: ArrayList<*>,
    private val tour_duration: ArrayList<*>,
    private val tour_date: ArrayList<*>

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
        holder.tour_id_txt.text = tour_id[position].toString()
        holder.tour_start_txt.text = tour_start[position].toString()
        holder.tour_destination_txt.text = tour_destination[position].toString()
        holder.tour_duration_txt.text = tour_duration[position].toString()
        holder.tour_date_txt.text = tour_date[position].toString()

    }

    override fun getItemCount(): Int {
        return tour_id.size
    }

    inner class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tour_id_txt: TextView
        var tour_start_txt: TextView
        var tour_destination_txt: TextView
        var tour_duration_txt: TextView
        var tour_date_txt: TextView
        var mainLayout: LinearLayout

        init {
            tour_id_txt = itemView.findViewById(R.id.tourIDText)
            tour_start_txt = itemView.findViewById(R.id.tourStartText)
            tour_destination_txt = itemView.findViewById(R.id.tourDestinationText)
            tour_duration_txt = itemView.findViewById(R.id.tourDurationText)
            tour_date_txt = itemView.findViewById(R.id.tourDateText)

            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }

}