// MainActivity.kt
package com.example.myapplication

import android.content.Intent
import android.view.Window
import android.os.Bundle
import android.widget.CalendarView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

//Androidのアクティビティの基本クラスを継承
class MainActivity : AppCompatActivity() {
    private lateinit var calendarView: CalendarView
    private lateinit var voiceButton: FloatingActionButton
    private lateinit var taskButton: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    //private val SPEECH_REQUEST_CODE = 0
    //private val RECORD_AUDIO_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) // タイトルバーを非表示にする
        setContentView(R.layout.activity_main)

        setupCalendarView()
        setupVoiceButton()
        setupTaskButton()
        setupNavigationDrawer()

    }

    @Suppress("DEPRECATION")
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
        voiceButton.setOnClickListener {}
    }

    private fun setupTaskButton() {
        taskButton = findViewById(R.id.taskButton)
        taskButton.setOnClickListener {
            //タスク画面を開く
            val intent = Intent(this, TaskInputActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupNavigationDrawer() {
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

    // タスクの取得
    private fun showEventsForDate(year: Int, month: Int, dayOfMonth: Int) {
        val taskManager = TaskManager(this)
        val tasks = taskManager.getTasksForDate(year, month, dayOfMonth)
        // タスク一覧を表示するダイアログやフラグメントを表示
    }
}
