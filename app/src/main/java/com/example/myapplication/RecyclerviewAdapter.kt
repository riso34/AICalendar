package com.example.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerviewAdapter(daysInMonth: ArrayList<Long>) : RecyclerView.Adapter<ViewHolderItem>() {

    private var daysOfMonth = daysInMonth

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val oneXml = LayoutInflater.from(parent.context).inflate(R.layout.one_layout,parent,false)
        return ViewHolderItem(oneXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        if (daysOfMonth[position] == 0L) {
            holder.oneDayText.text = ""
        } else {
            holder.oneDayText.text = daysOfMonth[position].toString()
        }
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }
}