package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var calendarRecyclerView:RecyclerView
    private lateinit var monthYearText:TextView
    private lateinit var selectedDate:LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nextButton: Button = findViewById(R.id.nextButton)
        val previousButton:Button = findViewById(R.id.previousButton)

        initWidgets()
        selectedDate = LocalDate.now()
        setMonthView()

        previousButton.setOnClickListener {
            initWidgets()
            selectedDate = selectedDate.minusMonths(1)
            setMonthView()
        }

        nextButton.setOnClickListener {
            initWidgets()
            selectedDate = selectedDate.plusMonths(1)
            setMonthView()
        }


    }

    private fun initWidgets(){
        calendarRecyclerView = findViewById(R.id.rv)
        monthYearText = findViewById(R.id.monthText)
    }

    private fun setMonthView(){
        monthYearText.text = monthYear(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        val calendarAdapter = RecyclerviewAdapter(daysInMonth)
        val layoutManager: LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun daysInMonthArray(date: LocalDate?): ArrayList<Long> {
        val daysInMonthArray = ArrayList<Long>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42){
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(0)
            } else {
                daysInMonthArray.add((i - dayOfWeek).toLong())
            }
        }
        return  daysInMonthArray
    }

    private fun monthYear(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }
}

