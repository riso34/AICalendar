// MainActivity.kt
package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var voiceButton: FloatingActionButton
    private lateinit var taskButton: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private val SPEECH_REQUEST_CODE = 0
    private val RECORD_AUDIO_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupCalendarView()
        setupVoiceButton()
        setupTaskButton()

        // ツールバーの設定
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //ドロワーレイアウトの設定
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)

        // ハンバーガーメニューの設定
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // ナビゲーションアイテムのクリックリスナー
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_calendar -> {
                    // カレンダー画面の処理
                }
                R.id.nav_tasks -> {
                    // タスク一覧画面の処理
                }
                R.id.nav_settings -> {
                    // 設定画面の処理
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setupCalendarView() {
        calendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 日付が選択されたときの処理
            showEventsForDate(year, month, dayOfMonth)
        }
    }

    private fun setupVoiceButton() {
        voiceButton = findViewById(R.id.voiceButton)
        voiceButton.setOnClickListener {
            if (checkAudioPermission()) {
                startVoiceRecognition()
            } else {
                requestAudioPermission()
            }
        }
    }

    private fun setupTaskButton() {
        taskButton = findViewById(R.id.taskButton)
        taskButton.setOnClickListener {
            if (checkAudioPermission()) {
                startVoiceRecognition()
            } else {
                requestAudioPermission()
            }
        }
    }

    private fun checkAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestAudioPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_AUDIO_PERMISSION_CODE
        )
    }

    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "タスクを話してください")
        }
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            val spokenText = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)
            if (spokenText != null) {
                processVoiceInput(spokenText)
            }
        }
    }

    private fun processVoiceInput(text: String) {
        // 音声入力の解析とタスク作成
        val taskManager = TaskManager(this)
        val task = parseVoiceInput(text)
        if (task != null) {
            taskManager.addTask(task)
            Toast.makeText(this, "タスクを追加しました: ${task.title}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun parseVoiceInput(text: String): Task? {
        // 簡単な自然言語処理で音声入力を解析
        // 例: "明日の3時にミーティング"
        try {
            val words = text.split(" ")
            // 基本的なパターンマッチング
            // 実際のアプリではより高度なNLP処理が必要
            return Task(
                title = text,
                date = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, 1) }.time,
                description = ""
            )
        } catch (e: Exception) {
            Toast.makeText(this, "タスクの解析に失敗しました", Toast.LENGTH_SHORT).show()
            return null
        }
    }

    private fun showEventsForDate(year: Int, month: Int, dayOfMonth: Int) {
        val taskManager = TaskManager(this)
        val tasks = taskManager.getTasksForDate(year, month, dayOfMonth)
        // タスク一覧を表示するダイアログやフラグメントを表示
    }
}
