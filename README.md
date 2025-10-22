## **ToDo App: Lab 2 (Mobile Application Development)**

### **Overview**

This project is a **ToDo Android application** built using **Kotlin** and **Jetpack Compose**.  
 It allows users to create, view, search, and delete tasks with **persistent storage using Room Database**, **color-coded task cards**, and **calendar-based deadline selection**.

The app is designed following the **Model-View-Controller (MVC)** pattern, separating UI, business logic, and database interactions for scalability and maintainability.

---

## **Objectives**

In this lab, you will:

* Build a **database** and save tasks **permanently**  
* Add a **color picker** for tasks  
* Display a **list of tasks** on the home screen  
* Integrate a **calendar-based deadline selector**  
* Implement **search/filtering by title**  
* Use **dynamic sizing and real-time updates**

---

## **Features Implemented**

| Feature | Description |
| ----- | ----- |
| **Room Database Integration** | Created Task entity, DAO, and AppDatabase for permanent storage |
| **Task Persistence** | Tasks remain saved even after app restart |
| **Add New Task Screen** | Input title, description, deadline (via calendar), and color |
| **Color Picker** | Select custom color for each task |
| **Calendar Deadline** | Pick date using Android’s DatePickerDialog |
| **Search Functionality** | Search tasks dynamically by title |
| **Dynamic UI Update** | Home screen updates immediately after adding or deleting a task |
| **Error Handling** | Prevent saving empty titles |
| **Material 3 UI Styling** | Modern theme with primary/secondary colors and rounded UI |
| **Dynamic Task Cards** | Each card displays color, title, description, and deadline |

---

##  **Architecture (MVC)**

| Layer | Component | Responsibility |
| ----- | ----- | ----- |
| **Model** | `Task.kt` | Defines task entity and database table |
| **View** | `HomeScreen.kt`, `NewTaskScreen.kt` | Compose UI for viewing and adding tasks |
| **Controller / ViewModel** | `TaskViewModel.kt`, `TaskRepository.kt` | Handles data logic, LiveData/Flow observation |
| **Database Access Object** | `TaskDao.kt` | Manages CRUD operations with Room |
| **Database** | `AppDatabase.kt` | Initializes and configures the Room database |

---

## **Project Structure**

`app/`  
 `├── src/main/java/com/example/todo/`  
 `│   ├── MainActivity.kt`  
 `│   ├── HomeScreen.kt`  
 `│   ├── NewTaskScreen.kt`  
 `│   ├── Task.kt`  
 `│   ├── TaskDao.kt`  
 `│   ├── TaskRepository.kt`  
 `│   ├── TaskViewModel.kt`  
 `│   └── AppDatabase.kt`  
 `│`  
 `├── res/values/`  
 `│   ├── colors.xml`  
 `│   ├── themes.xml`  
 `│   └── strings.xml`  
 `│`  
 `└── ui/theme/`  
     `├── Color.kt`  
     `├── Theme.kt`  
     `└── Type.kt`

---

## **UI Design Highlights**

* **Modern Material 3 theme** with consistent typography and color scheme.

* **Custom primary, secondary, and surface colors** defined in `colors.xml` and applied via `ToDoTheme.kt`.

* **TopAppBar** and **FloatingActionButton** follow Material 3 design guidelines.

* **Rounded Cards** with shadow elevation and color-coded backgrounds.

---

## **Dependencies**

| Library | Purpose |
| ----- | ----- |
| **Jetpack Compose** | Modern UI toolkit for declarative layouts |
| **Material 3** | Design system components and theming |
| **Room Database** | Local persistent storage |
| **Navigation Compose** | In-app navigation between screens |
| **ViewModel \+ LiveData** | Data handling and lifecycle awareness |
| **Kotlin Coroutines** | Asynchronous operations |
| **JUnit \+ Espresso** | Testing support |

### **Key Versions**

* **Compose BOM:** 2024.09.01

* **Room:** 2.6.1

* **Kotlin:** 1.9+

* **Compile SDK:** 36

* **Target SDK:** 36

---

## **How to Run**

**Clone the Repository**

 `git clone https://github.com/yourusername/ToDoApp-Lab2.git`  
`cd ToDoApp-Lab2`

1. **Open in Android Studio**

   * Use **Android Studio Hedgehog or newer**  
   * Let Gradle sync dependencies

2. **Build & Run**

   * Select an emulator or connect your phone  
   * Press ▶️

3. **Test Functionality**

   * Add new tasks with title, description, color, and deadline  
   * Verify tasks persist after restart  
   * Use the search bar to filter by title

---

##  **Example Usage**

1. Tap the ➕ to open the **New Task** screen.  
2. Enter a **title** and **description**.  
3. Choose a **deadline** using the calendar picker.  
4. Select a **task color** from the circular color palette.  
5. Click **Save Task**, the task appears on the home screen instantly.  
6. Use the **search bar** to filter your tasks.  
7. Tap **Delete** to remove a task from the database.

---

## **Final Summary**

| Requirement | Implemented In |
| ----- | ----- |
| Database setup & classes | `AppDatabase.kt`, `TaskDao.kt`, `TaskRepository.kt` |
| Permanent storage | Room \+ ViewModel |
| Display saved tasks | `HomeScreen.kt` |
| Search/filter by title | `HomeScreen.kt` |
| Dynamic size and updates | Compose LazyColumn |
| Color picker and saving | `NewTaskScreen.kt` |
| Validation (title required) | `NewTaskScreen.kt` |
| Modern UI styling | `themes.xml`, `ToDoTheme.kt` |
| MVC structure | Model, View, ViewModel, DAO |

