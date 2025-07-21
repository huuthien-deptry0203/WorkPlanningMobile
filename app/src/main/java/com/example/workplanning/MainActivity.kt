package com.example.workplanning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") {
                            LoginScreen(navController, userViewModel)
                        }
                        composable("register") {
                            RegisterScreen(navController, userViewModel)
                        }
                        composable("home") {
                            HomeScreen(viewModel, navController)
                        }

                        composable("taskDetail/{taskId}") {
                          val taskId = it.arguments?.getString("taskId")
                          TaskDetailScreen(viewModel, navController, taskId)
                         }
                        composable("addTask") {
                            AddTaskScreen(viewModel, navController)
                        }
                        composable("stats") {
                            StatsScreen(viewModel)
                        }

                    }
                }
            }
        }
    }
}
