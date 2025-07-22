package com.example.workplanning.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    val colorScheme = MaterialTheme.colorScheme

    val mauNen = listOf(
        colorScheme.surface,
        colorScheme.background,
        colorScheme.surface,

        )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(mauNen)),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = colorScheme.primary)
        } else {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "🧾 Đăng ký tài khoản",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.primary
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username", color = colorScheme.primary) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorScheme.primary,
                        unfocusedTextColor =  colorScheme.primary,
                        focusedBorderColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = colorScheme.primary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorScheme.primary,
                        unfocusedTextColor =  colorScheme.primary,
                        focusedBorderColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = colorScheme.primary) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = colorScheme.primary,
                        unfocusedTextColor =  colorScheme.primary,
                        focusedBorderColor = colorScheme.primary,
                        focusedLabelColor = colorScheme.primary
                    )
                )

                if (isError) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(errorMessage, color = colorScheme.error)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (username.length < 4) {
                            isError = true
                            errorMessage = "Tên người dùng quá ngắn"
                            return@Button
                        }
                        if (password.length < 4) {
                            isError = true
                            errorMessage = "Mật khẩu phải ≥ 4 ký tự"
                            return@Button
                        }
                        if (password != confirmPassword) {
                            isError = true
                            errorMessage = "Mật khẩu không khớp"
                            return@Button
                        }

                        val success = userViewModel.register(username, password)
                        if (!success) {
                            isError = true
                            errorMessage = "Tên người dùng đã tồn tại"
                            return@Button
                        }

                        isLoading = true
                        isError = false
                        scope.launch {
                            delay(1500)
                            navController.navigate("login")
                            isLoading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.secondary,   // Màu nền nút
                        contentColor = colorScheme.primary        // Màu chữ trong nút
                    )
                ) {
                    Text(text = "Tạo tài khoản")
                }


                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Đã có tài khoản? Đăng nhập",
                    color = colorScheme.primary,
                    modifier = Modifier.clickable { navController.navigate("login") }
                )
            }
        }
    }
}
