package com.example.workplanning.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.example.workplanning.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import com.example.workplanning.ui.theme.UserViewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.Date
import java.util.Locale


@Composable
fun StatsScreen(
    viewModel: TaskViewModel,
    userViewModel: UserViewModel
) {
    val currentUsername = userViewModel.currentUsername
    val tasks = viewModel.todayTasks.collectAsState().value
        .filter { it.username == currentUsername }

    val completed = tasks.count { it.isDone }
    val pending = tasks.size - completed
    val total = tasks.size

    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    val overdueTasks = tasks.filter {
        !it.isDone && it.date < currentDate
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF74EBD5), Color(0xFFACB6E5)))
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Thống kê công việc",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Đã hoàn thành: $completed", color = Color.White, fontSize = 18.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Chưa hoàn thành: $pending", color = Color.White, fontSize = 18.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Tổng công việc: $total", color = Color.White, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (total > 0) {
                DonutChart(completed, total)
                Spacer(modifier = Modifier.height(32.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Công việc quá hạn:",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

            if (overdueTasks.isEmpty()) {
                Text("Không có công việc quá hạn", color = Color.White)
            } else {
                overdueTasks.forEach {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${it.title} (${it.date})", color = Color.Red, fontSize = 17.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun DonutChart(completed: Int, total: Int) {
    val percentage = completed.toFloat() / total
    val angle = percentage * 360f

    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(160.dp)) {
            // Vòng ngoài màu xám (toàn bộ)
            drawArc(
                color = Color.LightGray,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round)
            )
            // Vòng hoàn thành màu xanh
            drawArc(
                color = Color(0xFF4CAF50),
                startAngle = -90f,
                sweepAngle = angle,
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round)
            )
        }
        Text(
            text = "${(percentage * 100).toInt()}%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
