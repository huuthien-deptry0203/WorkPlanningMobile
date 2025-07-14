package com.example.workplanning.ui.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
fun TaskDetailScreen(viewModel: TaskViewModel, navController: NavHostController, taskId: String?) {
    val task = remember(taskId) { viewModel.getTaskById(taskId ?: "") }

    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    var editedTitle by remember { mutableStateOf(task?.title ?: "") }
    var editedDescription by remember { mutableStateOf(task?.description ?: "") }
    var editedDate by remember { mutableStateOf(task?.date ?: "") }
    var isDone by remember { mutableStateOf(task?.isDone ?: false) }

    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    // Time Picker
    fun showTimePicker(onTimeSelected: () -> Unit) {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                editedDate = dateFormatter.format(calendar.time)
                onTimeSelected()
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Date Picker
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            calendar.set(y, m, d)
            showTimePicker {}
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    task?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFFFBD3E9), Color(0xFFBB377D))))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Description, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chỉnh sửa công việc",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                OutlinedTextField(
                    value = editedTitle,
                    onValueChange = { editedTitle = it },
                    label = { Text("Tên công việc", color = Color.White.copy(alpha = 0.9f)) },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    leadingIcon = {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = editedDate,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hạn công việc", color = Color.White.copy(alpha = 0.9f)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Chọn ngày giờ",
                            tint = Color.White,
                            modifier = Modifier.clickable { datePickerDialog.show() }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            datePickerDialog.show()
                        },
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                OutlinedTextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    label = { Text("Mô tả công việc", color = Color.White.copy(alpha = 0.9f)) },
                    leadingIcon = {
                        Icon(Icons.Default.Notes, contentDescription = null, tint = Color.White)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(120.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.White),
                    shape = MaterialTheme.shapes.small,
                    maxLines = Int.MAX_VALUE,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.White,
                        cursorColor = Color.White
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Checkbox(
                        checked = isDone,
                        onCheckedChange = { isDone = it },
                        colors = CheckboxDefaults.colors(checkedColor = Color.White)
                    )
                    Text("Đã hoàn thành", color = Color.White)
                }

                Button(
                    onClick = {
                        viewModel.updateTask(task.id, editedTitle, editedDate, editedDescription, isDone)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Lưu thay đổi", color = Color.White)
                }

                Button(
                    onClick = {
                        viewModel.deleteTask(task.id)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Xoá công việc", color = Color.White)
                }
            }
        }
    }
}
