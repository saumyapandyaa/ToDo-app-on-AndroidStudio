package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.theme.ToDoTheme

class HomeActivity : ComponentActivity() {

    private val taskViewModel: TaskViewModel by viewModels()  // ← Connect ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ToDoTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    // 🏠 Home Screen (List of tasks)
                    composable("home") {
                        HomeScreen(
                            navController = navController,
                            viewModel = taskViewModel
                        )
                    }

                    // ➕ Add Task Screen
                    composable("newTask") {
                        NewTaskScreen(
                            navController = navController,
                            onAddTask = { task ->
                                taskViewModel.addTask(task)
                            }
                        )
                    }
                }
            }
        }
    }
}
