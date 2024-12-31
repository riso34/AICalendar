package com.example.myapplication

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat

class TaskInputActivity : AppCompatActivity() {
    private lateinit var titleInput: TextInputEditText
    private lateinit var startTimeInput: EditText
    private lateinit var endTimeInput: EditText
    private lateinit var contentInput: TextInputEditText
    private lateinit var notificationSwitch: SwitchMaterial
    private lateinit var saveButton: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_input)

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
                val formattedTime = formatTime(hour, minute)
                startTimeInput.setText(formattedTime)
            }
        }

        endTimeInput.setOnClickListener {
            showTimePickerDialog { hour, minute ->
                val formattedTime = formatTime(hour, minute)
                endTimeInput.setText(formattedTime)
            }
        }

        saveButton.setOnClickListener {
            val title = titleInput.text.toString()
            val startTime = startTimeInput.text.toString()
            val endTime = endTimeInput.text.toString()
            val content = contentInput.text.toString()
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

    //時間をフォーマットする関数
    private fun formatTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}