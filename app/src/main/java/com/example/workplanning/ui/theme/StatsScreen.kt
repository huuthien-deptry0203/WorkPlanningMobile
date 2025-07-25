package com.example.workplanning.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
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

    val mauNen = listOf(
        colorScheme.surface,
        colorScheme.background,
        colorScheme.surface,
    )
    val textColor = colorScheme.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(mauNen)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = textColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Thống kê công việc",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, contentDescription = null, tint = textColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Đã hoàn thành: $completed",
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = textColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Chưa hoàn thành: $pending",
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = textColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Tổng công việc: $total",
                    color = textColor,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (total > 0) {
                DonutChart(completed, total)
                Spacer(modifier = Modifier.height(32.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = textColor)

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Công việc quá hạn: ${overdueTasks.size}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (overdueTasks.isEmpty()) {
                Text("Không có công việc quá hạn", color = textColor)
            } else {
                overdueTasks.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = colorScheme.error)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("${it.title} (${it.date})", color = colorScheme.error)

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
    val colorScheme = colorScheme

    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(160.dp)) {
            // Vòng ngoài
            drawArc(
                color = colorScheme.primary,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round)
            )
            // Vòng hoàn thành
            drawArc(
                color = colorScheme.onBackground,
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
            color = colorScheme.primary
        )
    }
}
