package com.example.todo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository(private val dao: TaskDao) {

    // Read tasks
    fun getAllTasks(): Flow<List<Task>> = flow {
        emit(dao.getAllTasks())
    }

    // Add a new task
    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    // Delete a task
    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }
}
