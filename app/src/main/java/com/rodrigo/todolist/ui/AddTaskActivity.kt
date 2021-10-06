package com.rodrigo.todolist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rodrigo.todolist.databinding.ActivityAddTaskBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.rodrigo.todolist.extensions.format
import com.rodrigo.todolist.extensions.text
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
    }
}