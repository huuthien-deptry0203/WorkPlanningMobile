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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.workplanning.ui.theme.AddTaskScreen
import com.example.workplanning.ui.theme.HomeScreen
import com.example.workplanning.ui.theme.LoginScreen
import com.example.workplanning.ui.theme.RegisterScreen
import com.example.workplanning.ui.theme.StatsScreen
import com.example.workplanning.ui.theme.TaskDetailScreen
import com.example.workplanning.ui.theme.TaskViewModelFactory
import com.example.workplanning.ui.theme.UserViewModel
import com.example.workplanning.ui.theme.WorkPlanningTheme
import com.example.workplanning.ui.theme.TaskViewModel

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
                        .fillMaxSize()
                        .safeDrawingPadding(),
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
                                StatsScreen(viewModel, navController, userViewModel)
                            }
                        }
                    } else {
                        val mauNen = listOf(
                            colorScheme.surface,
                            colorScheme.background,
                            colorScheme.surface,
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.verticalGradient(mauNen)),
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
