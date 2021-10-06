package com.rodrigo.todolist.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rodrigo.todolist.databinding.ActivityAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import com.rodrigo.todolist.datasource.TaskDataSource
import com.rodrigo.todolist.extensions.format
import com.rodrigo.todolist.extensions.text
import com.rodrigo.todolist.model.Task
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()

    }
    private fun insertListeners() {
        binding.txtDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offSet = timeZone.getOffset(Date().time) * -1
                binding.txtDate.text = Date(it + offSet).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        binding.txtTime.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
               val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
               val hour = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
                binding.txtTime.text ="$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }
        binding.btnCancelar.setOnClickListener {
            finish()
        }
        binding.btnTarefa.setOnClickListener {
            val task = Task(
                title = binding.txtTitle.text,
                date = binding.txtDate.text,
                hour = binding.txtTime.text
            )
            TaskDataSource.insertTask(task)
            Log.e("TAG","insertListeners: " + TaskDataSource.getList())
        }
    }
}