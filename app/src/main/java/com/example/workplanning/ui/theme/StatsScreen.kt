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

@Composable
fun StatsScreen(viewModel: TaskViewModel) {
    val tasks = viewModel.todayTasks.collectAsState().value
    val completed = tasks.count { it.isDone }
    val pending = tasks.size - completed
    val total = tasks.size

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val today = LocalDate.now()

    val overdueTasks = tasks.filter {
        !it.isDone && runCatching {
            LocalDate.parse(it.date.substring(0, 10)).isBefore(today)
        }.getOrDefault(false)
    }

    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
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
                Icon(Icons.Default.BarChart, contentDescription = null, tint = colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Thống kê công việc",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Check, contentDescription = null, tint = colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Đã hoàn thành: $completed",
                    color = colorScheme.primary,
                    fontSize = 18.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Chưa hoàn thành: $pending",
                    color = colorScheme.onBackground,
                    fontSize = 18.sp
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.BarChart, contentDescription = null, tint = colorScheme.primary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Tổng công việc: $total",
                    color = colorScheme.onBackground,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (total > 0) {
                DonutChart(completed, total)
                Spacer(modifier = Modifier.height(32.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = colorScheme.tertiary)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Công việc quá hạn: ${overdueTasks.size}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (overdueTasks.isEmpty()) {
                Text("Không có công việc quá hạn", color = colorScheme.outline)
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
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = Modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(160.dp)) {
            // Vòng ngoài
            drawArc(
                color = colorScheme.surfaceVariant,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 24f, cap = StrokeCap.Round)
            )
            // Vòng hoàn thành
            drawArc(
                color = colorScheme.primary,
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
            color = colorScheme.onBackground
        )
    }
}
