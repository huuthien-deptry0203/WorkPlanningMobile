package com.example.workplanning.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen(
    taskViewModel: TaskViewModel,
    userViewModel: UserViewModel,
    navController: NavHostController
) {
    val username = userViewModel.currentUsername

    val todayTasks = taskViewModel.todayTasks.collectAsState().value
    val tasks = todayTasks.filter { it.username == username }
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val mauNen = listOf(
        colorScheme.surface,
        colorScheme.background,
        colorScheme.surface,
        )
    val textColor = colorScheme.primary

    if (username == null) {
        Text(
            "Vui lòng đăng nhập!",
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(mauNen)
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 32.dp,
                    bottom = 60.dp,
                    start = 16.dp,
                    end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Calendar Icon",
                    tint = colorScheme.primary,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Công việc của tôi",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(tasks) { task ->
                    val isOverdue = !task.isDone && task.date < currentDate

                    val cardColor = when {
                        task.isDone -> colorScheme.onBackground
                        isOverdue -> colorScheme.onErrorContainer
                        else -> colorScheme.secondary
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("taskDetail/${task.id}")
                            },
                        colors = CardDefaults
                            .cardColors(
                                containerColor = cardColor
                            ),
                        elevation = CardDefaults
                            .cardElevation(
                                defaultElevation = 4.dp
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                task.title,
                                fontSize = 20.sp,
                                color = textColor
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Due Date",
                                    tint = textColor,
                                    modifier = Modifier.size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))
                                Text(task.date, fontSize = 14.sp, color = textColor)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                val statusIcon = if (task.isDone)
                                    Icons.Default.CheckCircle else Icons.Default.Pending

                                val statusText = when {
                                    task.isDone -> "Hoàn thành"
                                    isOverdue -> "ĐÃ QUÁ HẠN"
                                    else -> "Chưa hoàn thành"
                                }

                                val statusColor = when {
                                    task.isDone -> colorScheme.onTertiary
                                    isOverdue -> colorScheme.error
                                    else -> textColor
                                }

                                Icon(
                                    imageVector = statusIcon,
                                    contentDescription = "Status",
                                    tint = statusColor,
                                    modifier = Modifier.size(16.dp)
                                )

                                Spacer(modifier = Modifier.width(4.dp))

                                Text(
                                    statusText,
                                    fontSize = 14.sp,
                                    color = statusColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { navController.navigate("addTask") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,   // Màu nền nút
                        contentColor = colorScheme.primary        // Màu chữ trong nút
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm", tint = textColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Thêm công việc",
                        fontSize = 10.sp,
                        color = textColor,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Button(
                    onClick = { navController.navigate("stats") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,
                        contentColor = colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Icon(Icons.Default.BarChart, contentDescription = "Thống kê", tint = textColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Thống kê",
                        fontSize = 10.sp,
                        color = textColor,
                        maxLines = 1
                    )
                }
            }
            Button(
                onClick = {
                    taskViewModel.clearUiState()
                    userViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.onErrorContainer,
                    contentColor = colorScheme.primary
                ),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Đăng xuất",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Đăng xuất", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}
