package com.example.todo

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.io.File
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    navController: NavController,
    taskId: Int,
    viewModel: TaskViewModel
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var deadline by rememberSaveable { mutableStateOf("") }
    var color by rememberSaveable { mutableStateOf("#64B5F6") }
    var imageUri by rememberSaveable { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    // Load the task from DB
    LaunchedEffect(taskId) {
        viewModel.getTaskById(taskId) { task ->
            task?.let {
                title = it.title
                description = it.description
                deadline = it.deadline
                color = it.color
                imageUri = it.imageUri
            }
        }
    }

    // Helper function to persist any image (upload or capture)
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
        topBar = { TopAppBar(title = { Text("Edit Task") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val updatedTask = Task(
                    id = taskId,
                    title = title,
                    description = description,
                    deadline = deadline,
                    color = color,
                    imageUri = imageUri
                )
                viewModel.updateTask(updatedTask)
                navController.popBackStack()
            }) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            if (!imageUri.isNullOrEmpty()) {
                AsyncImage(
                    model = File(imageUri!!),
                    contentDescription = "Task image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
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

            Button(
                onClick = {
                    viewModel.getTaskById(taskId) { t -> t?.let { viewModel.deleteTask(it) } }
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
                Spacer(Modifier.width(8.dp))
                Text("Delete Task")
            }

            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Discard Changes")
            }
        }
    }
}
