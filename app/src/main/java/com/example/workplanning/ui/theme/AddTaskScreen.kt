package com.example.workplanning.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.workplanning.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddTaskScreen(viewModel: TaskViewModel, navController: NavHostController) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    val calendar = remember { Calendar.getInstance() }
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    var date by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    var hour by remember { mutableStateOf(calendar.get(Calendar.HOUR_OF_DAY)) }
    var minute by remember { mutableStateOf(calendar.get(Calendar.MINUTE)) }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, h, m ->
                hour = h
                minute = m
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                date = format.format(calendar.time)
            },
            hour, minute, true
        )
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                calendar.set(y, m, d)
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    "Thêm công việc mới",
                    fontSize = 26.sp,
                    color = colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Tên công việc") },
                    textStyle = TextStyle(fontSize = 18.sp, color = colorScheme.onBackground),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        cursorColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedTextField(
                    value = date,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hạn công việc") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Chọn ngày và giờ",
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .clickable { datePickerDialog.show() },
                            tint = colorScheme.primary
                        )
                    },
                    textStyle = TextStyle(fontSize = 18.sp, color = colorScheme.onBackground),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        cursorColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Mô tả công việc") },
                    textStyle = TextStyle(fontSize = 16.sp, color = colorScheme.onBackground),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorScheme.primary,
                        unfocusedBorderColor = colorScheme.outline,
                        cursorColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                AnimatedVisibility(visible = error.isNotEmpty()) {
                    Text(error, color = colorScheme.error, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        val dateRegex = Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")
                        if (title.isBlank() || date.isBlank()) {
                            error = "Vui lòng nhập đầy đủ tên và hạn công việc"
                        } else if (!date.matches(dateRegex)) {
                            error = "Ngày không đúng định dạng YYYY-MM-DD HH:mm"
                        } else {
                            error = ""
                            viewModel.addTask(title, date, description)
                            navController.popBackStack()
                        }
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
                    elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                ) {
                    Text("Thêm công việc", color = colorScheme.onPrimary)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
