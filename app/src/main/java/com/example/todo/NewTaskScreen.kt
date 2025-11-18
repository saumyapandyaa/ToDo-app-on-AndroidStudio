package com.example.todo

import android.app.DatePickerDialog
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    navController: NavController,
    onAddTask: (Task) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var selectedColor by remember { mutableStateOf(Color(0xFF64B5F6)) }
    var deadline by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // ✅ Helper: Save image permanently in internal storage
    fun persistImage(context: android.content.Context, input: InputStream): String {
        val file = File(context.filesDir, "${System.currentTimeMillis()}.jpg")
        file.outputStream().use { output: OutputStream -> input.copyTo(output) }
        return file.absolutePath
    }

    // 🖼 Upload from gallery
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val input = context.contentResolver.openInputStream(it)
                if (input != null) {
                    val savedPath = persistImage(context, input)
                    imageUri = savedPath
                }
            }
        }

    // 📷 Capture from camera
    val photoFile = remember { File(context.filesDir, "photo_${System.currentTimeMillis()}.jpg") }
    val photoUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)

    val takePhotoLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                imageUri = photoFile.absolutePath // ✅ Persistent path
            } else {
                Toast.makeText(context, "Capture cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    val requestCameraPermission =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                takePhotoLauncher.launch(photoUri)
            } else {
                Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

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

            // 🖼 Image preview + buttons
            if (!imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = File(imageUri!!),
                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { pickImageLauncher.launch("image/*") }) {
                    Text("Upload Image")
                }
                Button(onClick = {
                    requestCameraPermission.launch(android.Manifest.permission.CAMERA)
                }) {
                    Text("Capture Image")
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

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
                        val red = (selectedColor.red * 255).toInt()
                        val green = (selectedColor.green * 255).toInt()
                        val blue = (selectedColor.blue * 255).toInt()
                        val hexColor = String.format("#%02X%02X%02X", red, green, blue)

                        val task = Task(
                            title = title.text,
                            deadline = deadline,
                            description = description.text,
                            color = hexColor,
                            imageUri = imageUri
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
