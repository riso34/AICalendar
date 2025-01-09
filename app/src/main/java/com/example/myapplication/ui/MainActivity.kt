// MainActivity.kt
package com.example.myapplication.ui

import android.content.Intent
import android.content.SharedPreferences
import android.view.Window
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.Task
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.utils.Constants
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

//Androidのアクティビティの基本クラスを継承
class MainActivity : AppCompatActivity(), TaskAdapter.TaskClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarDate: String

    private val viewModel: TaskViewModel by viewModels()
    lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE) // タイトルバーを非表示にする

        // activity_main画面のインスタンス化
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCalendarDate()
        initUI()
        setupNavigationDrawer()
        updateTaskList()
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onItemClicked(task: Task) {
        Log.d("TaskAdapter", "onItemClicked: $task")
    }

    // 現在選択されている日付取得
    private fun getCalendarDate() {
        val selectedDate = binding.calendarView.date
        val calendar = Calendar.getInstance().apply { timeInMillis = selectedDate }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        calendarDate = Constants.formatDate(year, month, dayOfMonth)
    }

    //　画面初期化
    private fun initUI() {
        binding.tasksList.setHasFixedSize(true)
        binding.tasksList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = TaskAdapter(this)
        binding.tasksList.adapter = adapter

        // カレンダー画面処理
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            calendarDate = Constants.formatDate(year, month, dayOfMonth)
            updateTaskList()
        }

        // タスクボタン処理
        binding.taskButton.setOnClickListener {
            val intent = Intent(this, TaskInputActivity::class.java).apply {
                putExtra("EXTRA_DATE", calendarDate)
            }
            startActivity(intent)
        }

    }

    private fun setupNavigationDrawer() {
        // ツールバーの設定
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //ドロワーレイアウトの設定
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView

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

    // taskList画面の更新
    private fun updateTaskList() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.getAllTasks(calendarDate).collect { tasks ->
                adapter.updateList(tasks)
            }
        }
    }
}

