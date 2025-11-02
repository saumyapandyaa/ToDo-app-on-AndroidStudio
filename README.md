## **ToDo App: Mobile Application Development**

### **Overview**
This project is a **ToDo Android application** built using **Kotlin** and **Jetpack Compose**.  
It allows users to **create, view, edit, delete, search, and manage tasks** with full **persistent storage using Room Database**, **color-coded task cards**, **image attachments**, and **calendar-based deadline selection**.

The app follows the **Model-View-ViewModel (MVVM)** architecture pattern, ensuring a clean separation of UI, logic, and data layers for scalability and maintainability.

---

### **Objectives**
By the end of this lab, the app implements:
- ✅ **CRUD operations** (Create, Read, Update, Delete) with Room Database  
- ✅ **Edit task screen** supporting image upload/capture  
- ✅ **Color picker** and **calendar-based deadline** selection  
- ✅ **Persistent image saving** using `FileProvider`  
- ✅ **Dynamic UI updates** with reactive state management  
- ✅ **Search/filter by task title**

---

### **Features Implemented**

| Feature | Description |
|----------|-------------|
| **Room Database Integration** | Created `Task` entity, `TaskDao`, and `AppDatabase` for permanent task storage |
| **CRUD Functionality** | Add, view, update, and delete tasks with immediate UI refresh |
| **Edit Task Screen** | Modify all fields of an existing task including title, description, deadline, color, and image |
| **Image Upload & Capture** | Upload images from gallery or capture directly via camera using `FileProvider` |
| **Task Persistence** | Tasks and attached images persist after app restart |
| **Search Functionality** | Filter tasks dynamically by title |
| **Color Picker** | Select custom color for each task card |
| **Calendar Deadline** | Set deadlines using Android’s `DatePickerDialog` |
| **Material 3 Design** | Modern UI theme with rounded corners, elevation, and consistent styling |
| **Error Handling** | Prevents saving empty titles and manages permission requests gracefully |

---

### **Architecture (MVVM)**

| Layer | Component | Responsibility |
|-------|------------|----------------|
| **Model** | `Task.kt` | Defines Room entity with fields: title, description, color, deadline, imageUri |
| **View** | `HomeScreen.kt`, `NewTaskScreen.kt`, `EditTaskScreen.kt` | Handles all Compose UI elements |
| **ViewModel** | `TaskViewModel.kt` | Manages app state, coroutine-based DB operations, and LiveData/Flow updates |
| **Repository** | `TaskRepository.kt` | Encapsulates data operations and abstracts access to the DAO |
| **DAO (Data Access Object)** | `TaskDao.kt` | Manages CRUD operations through SQL queries |
| **Database** | `AppDatabase.kt` | Configures and initializes the Room Database |

---

### **Project Structure**
```
app/
 ├── src/main/java/com/example/todo/
 │    ├── HomeActivity.kt
 │    ├── HomeScreen.kt
 │    ├── NewTaskScreen.kt
 │    ├── EditTaskScreen.kt
 │    ├── Task.kt
 │    ├── TaskDao.kt
 │    ├── TaskRepository.kt
 │    ├── TaskViewModel.kt
 │    └── AppDatabase.kt
 │
 ├── res/xml/
 │    └── file_paths.xml        # Required for FileProvider (camera storage)
 │
 ├── res/values/
 │    ├── colors.xml
 │    ├── themes.xml
 │    └── strings.xml
 │
 └── ui/theme/
      ├── Color.kt
      ├── Theme.kt
      └── Type.kt
```
---

### **UI Design Highlights**
- Material 3 styling with **TopAppBar**, **FloatingActionButton**, and **rounded Cards**.  
- **Coil’s `AsyncImage`** used to load and display task images.  
- **Color-coded backgrounds** and **deadline badges** for quick task identification.  
- **Real-time task updates** using Kotlin Coroutines and StateFlow.  

---

### **Dependencies**

| Library | Purpose |
|----------|----------|
| **Jetpack Compose** | Declarative UI toolkit |
| **Material 3** | Modern UI components and themes |
| **Room Database** | Persistent local data storage |
| **Navigation Compose** | In-app screen navigation |
| **ViewModel + StateFlow** | Lifecycle-aware data management |
| **Kotlin Coroutines** | Asynchronous database operations |
| **Coil Compose** | Load and display images efficiently |
| **JUnit + Espresso** | Unit and UI testing |

---

### **Key Versions**
- **Compose BOM:** 2024.09.01  
- **Room:** 2.6.1  
- **Coil:** 2.6.0  
- **Kotlin:** 1.9 +  
- **Compile SDK:** 36  
- **Target SDK:** 36  

---

### **How to Run**
1. **Clone the Repository**
   ```bash
   git clone https://github.com/saumyapandyaa/Simple-ToDo-app-on-AndroidStudio.git
   cd Simple-ToDo-app-on-AndroidStudio
   ```
2. **Open in Android Studio**
   - Use **Android Studio Hedgehog or newer**  
   - Allow Gradle sync to complete  
3. **Build & Run**
   - Select an emulator or connect an Android device  
   - Click **▶️ Run**
4. **Test Key Features**
   - Add new tasks with a title, description, color, and deadline  
   - Edit tasks and attach images via upload or camera capture  
   - Delete tasks and verify database updates  
   - Restart app → verify tasks persist  

---

### **Example Usage**
1. Tap **➕** to open **New Task Screen**.  
2. Enter title, description, choose deadline, color, and image (optional).  
3. Tap **Save Task** → appears on Home Screen instantly.  
4. Tap a task → opens **Edit Task Screen** to modify or delete.  
5. Tap **Capture Image** → use camera; image persists in app storage.  
6. Search tasks using the **search bar** at the top.  

---

### **Final Summary**
| Requirement | Implemented In |
|--------------|----------------|
| Database setup & classes | `AppDatabase.kt`, `TaskDao.kt`, `TaskRepository.kt` |
| Permanent storage | Room + ViewModel |
| CRUD operations | `HomeScreen.kt`, `NewTaskScreen.kt`, `EditTaskScreen.kt` |
| Image upload & capture | `EditTaskScreen.kt`, `FileProvider` setup |
| Real-time updates | StateFlow in `TaskViewModel.kt` |
| Search/filter by title | `HomeScreen.kt` |
| UI styling (Material 3) | `themes.xml`, `ToDoTheme.kt` |
| MVVM architecture | Model, View, ViewModel, Repository |
