package com.example.todo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    init {
        val dao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        loadTasks()
    }

    private fun loadTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch {
            repository.insertTask(task)
            loadTasks() // refresh after insertion
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
            loadTasks() // refresh after deletion
        }
    }
}
