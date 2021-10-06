package com.rodrigo.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigo.todolist.databinding.ActivityMainBinding
import com.rodrigo.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adpater by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()
    }
    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adpater.listenerEdit = {
        }
        adpater.listenerDelete = {
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK){
            binding.rvTasks.adapter = adpater
            adpater.submitList(TaskDataSource.getList())
        }
    }
    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}