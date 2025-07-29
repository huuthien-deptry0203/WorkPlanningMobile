package com.example.workplanning.ui.theme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
fun TaskDetailScreen(
    taskViewModel: TaskViewModel,
    navController: NavHostController,
    taskId: String?
) {
    val uiState by taskViewModel.uiState.collectAsState()

//    val task = remember(taskId) { taskViewModel.getTaskById(taskId ?: "") }
    val task = taskViewModel.getTaskById(taskId ?: "")

    val calendar = remember { Calendar.getInstance() }
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) }

    var editedTitle by rememberSaveable { mutableStateOf(task?.title ?: "") }
    var editedDescription by rememberSaveable { mutableStateOf(task?.description ?: "") }
    var editedDate by rememberSaveable { mutableStateOf(task?.date ?: "") }
    var isDone by rememberSaveable { mutableStateOf(task?.isDone == true) }

    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }

    var isInitialized by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(task) {
        if (task != null && !isInitialized) {
            editedTitle = task.title
            editedDescription = task.description
            editedDate = task.date
            isDone = task.isDone
            isInitialized = true
        }
    }

    fun showTimePicker(onTimeSelected: () -> Unit) {
        TimePickerDialog(
            context,
            { _, hour: Int, minute: Int ->
                calendar[Calendar.HOUR_OF_DAY] = hour
              calendar[Calendar.MINUTE] = minute
                editedDate = dateFormatter.format(calendar.time)
                onTimeSelected()
            },
          calendar[Calendar.HOUR_OF_DAY],
          calendar[Calendar.MINUTE],
            true
        ).show()
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            calendar[y, m] = d
            showTimePicker {}
        },
      calendar[Calendar.YEAR],
      calendar[Calendar.MONTH],
      calendar[Calendar.DAY_OF_MONTH]
    )
    val mauNen = listOf(
        colorScheme.surface,
        colorScheme.background,
        colorScheme.surface,
    )
    val textColor = colorScheme.primary

    task?.let {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(mauNen)),
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
                    Icon(Icons.Default.Description, contentDescription = null, tint = textColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Chỉnh sửa công việc",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColor
                    )
                }

                OutlinedTextField(
                    value = editedTitle,
                    onValueChange = { editedTitle = it },
                    label = { Text("Tên công việc", color = textColor) },
                    modifier = Modifier.fillMaxWidth(0.9f),
                    textStyle = LocalTextStyle.current.copy(color = textColor),
                    leadingIcon = {
                        Icon(Icons.Default.Edit, contentDescription = null, tint = textColor)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor
                    )
                )

                OutlinedTextField(
                    value = editedDate,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Hạn công việc",
                        color = textColor) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Chọn ngày giờ",
                            tint = textColor,
                            modifier = Modifier.clickable { datePickerDialog.show() }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clickable(interactionSource = interactionSource, indication = null) {
                            datePickerDialog.show()
                        },
                    textStyle = TextStyle(color = textColor, fontSize = 18.sp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor
                    )
                )

                OutlinedTextField(
                    value = editedDescription,
                    onValueChange = { editedDescription = it },
                    label = { Text("Mô tả công việc", color = textColor) },
                    leadingIcon = {
                        Icon(imageVector = Icons.AutoMirrored.Filled.Notes, contentDescription = null, tint = textColor)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(120.dp),
                    textStyle = LocalTextStyle.current.copy(color = textColor),
                    shape = MaterialTheme.shapes.small,
                    maxLines = Int.MAX_VALUE,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Checkbox(
                        checked = isDone,
                        onCheckedChange = {
                            isDone = it
                        },
                        colors = CheckboxDefaults.colors(checkedColor = colorScheme.onBackground)
                    )
                    Text("Đã hoàn thành", color = colorScheme.onTertiary)
                }

                Button(
                    onClick = {
                        taskViewModel.updateTask(task.id, editedTitle, editedDate, editedDescription, isDone)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.onBackground),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null, tint = textColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Lưu thay đổi", color = textColor)
                }

                Button(
                    onClick = {
                        taskViewModel.deleteTask(task.id)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.onErrorContainer),
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = null, tint = textColor)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Xoá công việc", color = textColor)
                }

                Button(
                    onClick = {
                        taskViewModel.clearUiState()
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorScheme.secondary),
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

