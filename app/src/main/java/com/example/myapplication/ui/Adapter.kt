package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Task

class TaskAdapter(private val listener: TaskClickListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        private val taskList = ArrayList<Task>()
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.TaskViewHolder {
            return TaskViewHolder (
                LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
            )
        }

        override fun onBindViewHolder(holder: TaskAdapter.TaskViewHolder, position: Int) {
            val item = taskList[position]
            holder.title.text = item.title
            holder.startTime.text = item.startTime
            holder.endTime.text = item.endTime
            holder.Task_layout.setOnClickListener {
                listener.onItemClicked(taskList[holder.adapterPosition])
            }
        }

        override fun getItemCount(): Int {
            return taskList.size
        }

        fun updateList(newList: List<Task>){
            taskList.clear()
            taskList.addAll(newList)
            notifyDataSetChanged()
        }

        inner class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            val Task_layout = itemView.findViewById<CardView>(R.id.card_layout)
            val title = itemView.findViewById<TextView>(R.id.tv_title)
            val startTime = itemView.findViewById<TextView>(R.id.tv_start_time)
            val endTime = itemView.findViewById<TextView>(R.id.tv_end_time)
        }

        interface TaskClickListener {
            fun onItemClicked(task: Task)
        }
    }

