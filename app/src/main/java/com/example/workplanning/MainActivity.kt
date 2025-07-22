package com.example.workplanning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workplanning.ui.screens.AddTaskScreen
import com.example.workplanning.ui.screens.HomeScreen
import com.example.workplanning.ui.screens.LoginScreen
import com.example.workplanning.ui.screens.RegisterScreen
import com.example.workplanning.ui.screens.StatsScreen
import com.example.workplanning.ui.screens.TaskDetailScreen
import com.example.workplanning.ui.theme.TaskViewModelFactory
import com.example.workplanning.ui.theme.UserViewModel
import com.example.workplanning.ui.theme.WorkPlanningTheme
import com.example.workplanning.viewmodel.TaskViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkPlanningTheme {
                val navController = rememberNavController()
                val userViewModel = remember { UserViewModel(applicationContext) }
                val viewModel: TaskViewModel = viewModel(
                    factory = TaskViewModelFactory(context = applicationContext)
                )

                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (userViewModel.isInitialized) {
                        val startDestination = if (userViewModel.currentUsername != null) "home" else "login"

                        NavHost(navController = navController, startDestination = startDestination) {
                            composable("login") {
                                LoginScreen(navController, userViewModel)
                            }
                            composable("register") {
                                RegisterScreen(navController, userViewModel)
                            }
                            composable("home") {
                                HomeScreen(
                                    taskViewModel = viewModel,
                                    userViewModel = userViewModel,
                                    navController = navController
                                )
                            }
                            composable("taskDetail/{taskId}") {
                                val taskId = it.arguments?.getString("taskId")
                                TaskDetailScreen(viewModel, navController, taskId)
                            }
                            composable("addTask") {
                                AddTaskScreen(viewModel, navController, userViewModel)
                            }
                            composable("stats") {
                                StatsScreen(viewModel, userViewModel)
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(Color(0xFF4A00E0), Color(0xFF8E2DE2))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                CircularProgressIndicator()
                                Spacer(modifier = Modifier.height(12.dp))
                                Text("Đang tải dữ liệu...", color = Color.Gray)
                            }
                        }
                    }

                }
            }
        }
    }
}
