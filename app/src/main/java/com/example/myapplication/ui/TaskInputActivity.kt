package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.Task
import com.example.myapplication.data.TaskRepository
import com.example.myapplication.databinding.ActivityTaskInputBinding
import com.example.myapplication.utils.Constants
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class TaskInputActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskInputBinding
    private lateinit var titleInput: TextInputEditText
    private lateinit var startTimeInput: EditText
    private lateinit var endTimeInput: EditText
    private lateinit var contentInput: TextInputEditText
    private lateinit var notificationSwitch: SwitchMaterial
    private lateinit var saveButton: MaterialButton

    var calendarDate: String? =""
    val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intentからデータを取得
        calendarDate = intent.getStringExtra("EXTRA_DATE")

        //タスク入力画面の初期化処理
        setupTaskInput()
    }

    private fun setupTaskInput() {
        titleInput = findViewById(R.id.titleInput)
        startTimeInput = findViewById(R.id.startTimeInput)
        endTimeInput = findViewById(R.id.endTimeInput)
        contentInput = findViewById(R.id.contentInput)
        notificationSwitch = findViewById(R.id.notificationSwitch)
        saveButton = findViewById(R.id.saveButton)

        startTimeInput.setOnClickListener {
            showTimePickerDialog { hour, minute ->
                val formattedTime = Constants.formatTime(hour, minute)
                startTimeInput.setText(formattedTime)
            }
        }

        endTimeInput.setOnClickListener {
            showTimePickerDialog { hour, minute ->
                val formattedTime = Constants.formatTime(hour, minute)
                endTimeInput.setText(formattedTime)
            }
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val date = calendarDate
            val startTime = startTimeInput.text.toString()
            val endTime = endTimeInput.text.toString()
            val content = contentInput.text.toString()
            val notification = notificationSwitch.isChecked

            val task = Task(title=title, date = date, startTime=startTime, endTime=endTime, context=content, notification=notification)

            Log.d("MainActivity", "Task saved: $title, $date, $startTime, $endTime, $content, $notification")
            viewModel.saveTask(task)
            Toast.makeText(this, "タスクを保存しました", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // TimePickerを表示する関数
    private fun showTimePickerDialog(onTimeSelected: (hour: Int, minute: Int) -> Unit) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("時間を選択してください")
            .build()

        picker.show(supportFragmentManager, "TIME_PICKER")

        picker.addOnPositiveButtonClickListener {
            onTimeSelected(picker.hour, picker.minute)
        }
    }
}