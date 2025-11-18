package com.example.todo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TaskRepository(private val dao: TaskDao) {

    fun getAllTasks(): Flow<List<Task>> = flow {
        emit(dao.getAllTasks())
    }

    suspend fun insertTask(task: Task) {
        dao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        dao.deleteTask(task)
    }

    // 🔹 NEW
    suspend fun updateTask(task: Task) {
        dao.updateTask(task)
    }

    suspend fun getTaskById(id: Int): Task? {
        return dao.getTaskById(id)
    }
}
