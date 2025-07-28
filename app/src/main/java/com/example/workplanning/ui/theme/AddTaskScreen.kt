package com.example.workplanning.ui.theme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun AddTaskScreen(
    taskViewModel: TaskViewModel,
    navController: NavHostController,
    userViewModel: UserViewModel
) {
    val uiState by taskViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    val calendar = rememberSaveable { Calendar.getInstance() }
    val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    var hour by rememberSaveable { mutableIntStateOf(calendar[Calendar.HOUR_OF_DAY]) }
    var minute by rememberSaveable { mutableIntStateOf(calendar[Calendar.MINUTE]) }

    val mauNen = listOf(
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.background,
        MaterialTheme.colorScheme.surface,
    )
    val textColor = MaterialTheme.colorScheme.primary

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, h, m ->
                hour = h
                minute = m
                calendar[Calendar.HOUR_OF_DAY] = h
                calendar[Calendar.MINUTE] = m
                taskViewModel.onDateChange(format.format(calendar.time))
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
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(mauNen)),
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
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                OutlinedTextField(
                    value = uiState.title,
                    onValueChange = { taskViewModel.onTitleChange(it) },
                    label = { Text("Tên công việc", color = textColor) },
                    textStyle = TextStyle(fontSize = 18.sp, color = textColor),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedLabelColor = textColor
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedTextField(
                    value = uiState.date,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hạn công việc", color = textColor) },
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
                    textStyle = TextStyle(fontSize = 18.sp, color = textColor),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedLabelColor = textColor
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = { taskViewModel.onDescriptionChange(it) },
                    label = { Text("Mô tả công việc") },
                    textStyle = TextStyle(fontSize = 16.sp, color = textColor),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedLabelColor = textColor
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                AnimatedVisibility(visible = !uiState.error.isNullOrBlank()) {
                    Text(uiState.error ?: "", color = colorScheme.error, fontSize = 14.sp)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = {
                        val dateRegex = Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")
                        val username = userViewModel.currentUsername

                        when {
                            username == null -> {
                                taskViewModel.setError("Bạn chưa đăng nhập!")
                            }
                            uiState.title.isBlank() || uiState.date.isBlank() -> {
                                taskViewModel.setError("Vui lòng nhập đầy đủ tên và ngày")
                            }
                            !uiState.date.matches(dateRegex) -> {
                                taskViewModel.setError("Ngày không đúng định dạng YYYY-MM-DD HH:mm")
                            }
                            else -> {
                                taskViewModel.setError("")
                                taskViewModel.addTask(username)
                                navController.popBackStack()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.onBackground),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm", tint = textColor)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Thêm công việc", color = textColor)
                }
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        taskViewModel.clearUiState()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Quay lại",
                        tint = textColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Quay lại", color = textColor)
                }
            }
        }
    }
}

