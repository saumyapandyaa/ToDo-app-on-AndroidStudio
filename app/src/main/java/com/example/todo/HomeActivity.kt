package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.ToDoTheme
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.todo.HomeScreen
import com.example.todo.NewTaskScreen


class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                // Shared list of tasks
                val tasks = remember { mutableStateListOf<Task>() }

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {  HomeScreen(
                        navController = navController,
                        tasks = tasks
                    )
                    }
                    composable("newTask") {
                        NewTaskScreen(
                            navController = navController,
                            onAddTask = { task -> tasks.add(task) }   // <- we're passing onAddTask
                        )
                    }

                }
            }
        }
    }
}
