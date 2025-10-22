package com.example.todo

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    navController: NavController,
    onAddTask: (Task) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var selectedColor by remember { mutableStateOf(Color(0xFF64B5F6)) } // Default Blue
    var deadline by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.add_task)) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 📝 Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it; showError = false },
                label = { Text(stringResource(R.string.task_title)) },
                modifier = Modifier.fillMaxWidth()
            )

            if (showError) {
                Text(
                    "Title cannot be empty!",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 📅 Deadline Picker
            Button(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            deadline = String.format("%02d/%02d/%d", day, month + 1, year)
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    if (deadline.isBlank()) "Select Deadline" else "Deadline: $deadline",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🗒️ Description
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.task_description)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 🎨 Color Picker
            Text("Pick Task Color:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            val colorOptions = listOf(
                Color(0xFFE57373), // Red
                Color(0xFF81C784), // Green
                Color(0xFF64B5F6), // Blue
                Color(0xFFFFD54F), // Yellow
                Color(0xFFBA68C8), // Purple
                Color(0xFFFF8A65)  // Orange
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                colorOptions.forEach { color ->
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(color, CircleShape)
                            .clickable { selectedColor = color }
                            .border(
                                width = if (selectedColor == color) 4.dp else 1.dp,
                                color = if (selectedColor == color)
                                    MaterialTheme.colorScheme.onBackground
                                else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            // 💾 Save Task Button
            Button(
                onClick = {
                    if (title.text.isBlank()) {
                        showError = true
                    } else {
                        // ✅ Properly convert color to clean hex (RRGGBB)
                        val red = (selectedColor.red * 255).toInt()
                        val green = (selectedColor.green * 255).toInt()
                        val blue = (selectedColor.blue * 255).toInt()
                        val hexColor = String.format("#%02X%02X%02X", red, green, blue)

                        val task = Task(
                            title = title.text,
                            deadline = deadline,
                            description = description.text,
                            color = hexColor
                        )
                        onAddTask(task)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(stringResource(R.string.task_save), color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    }
}
