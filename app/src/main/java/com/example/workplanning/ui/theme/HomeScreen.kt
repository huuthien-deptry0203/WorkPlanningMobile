package com.example.workplanning.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workplanning.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun HomeScreen(viewModel: TaskViewModel, navController: NavHostController) {
    val tasks = viewModel.todayTasks.collectAsState().value
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF00C9FF), Color(0xFF92FE9D)))
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp, bottom = 60.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Calendar Icon",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Công việc của tôi",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
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
                        task.isDone -> Color(0xFFB2FFB2) // Xanh lá nhạt
                        isOverdue -> Color(0xFFFFCDD2)   // Đỏ nhạt
                        else -> Color.White              // Trắng
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("taskDetail/${task.id}")
                            },
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(task.title, fontSize = 20.sp, color = Color.Black)
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Due Date",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(task.date, fontSize = 14.sp, color = Color.DarkGray)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                val statusIcon =
                                    if (task.isDone) Icons.Default.CheckCircle else Icons.Default.Pending
                                val statusText = when {
                                    task.isDone -> "Hoàn thành"
                                    isOverdue -> "ĐÃ QUÁ HẠN"
                                    else -> "Chưa hoàn thành"
                                }
                                val statusColor = if (task.isDone) Color(0xFF2E7D32)
                                else if (isOverdue) Color.Red else Color.Gray

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


// ...

            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Button(
                    onClick = { navController.navigate("addTask") },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm", tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Thêm công việc",
                        fontSize = 10.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip // tránh xuống dòng
                    )
                }

                Button(
                    onClick = { navController.navigate("stats") },
                    modifier = Modifier.weight(1f),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
                ) {
                    Icon(Icons.Default.BarChart, contentDescription = "Thống kê", tint = Color.White)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Thống kê",
                        fontSize = 10.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Clip
                    )
                }
            }

        }
    }
}
