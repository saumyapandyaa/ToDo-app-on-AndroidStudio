package com.example.todo

import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
}
