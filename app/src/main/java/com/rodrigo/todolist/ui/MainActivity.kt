package com.rodrigo.todolist.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rodrigo.todolist.databinding.ActivityMainBinding
import com.rodrigo.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adpater by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTasks.adapter = adpater
        updateList()

        insertListeners()
    }
    private fun insertListeners() {
        binding.btnAdd.setOnClickListener {
            startActivityForResult(Intent(this, AddTaskActivity::class.java), CREATE_NEW_TASK)
        }
        adpater.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(AddTaskActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adpater.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }
    private fun updateList(){
        val list = TaskDataSource.getList()
        binding.emptyInclude.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
        else View.GONE
        adpater.submitList(list)
    }
    companion object {
        private const val CREATE_NEW_TASK = 1000
    }
}