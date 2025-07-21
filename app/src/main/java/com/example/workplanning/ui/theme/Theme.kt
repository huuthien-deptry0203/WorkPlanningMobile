package com.example.workplanning.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎨 Màu tối – dịu mắt, nền xám xanh đậm, chữ trắng
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF81D4FA),        // Xanh dương nhạt
    onPrimary = Color.Black,
    secondary = Color(0xFF4DD0E1),      // Xanh ngọc nhạt
    onSecondary = Color.Black,
    background = Color(0xFF121212),     // Xám rất đậm
    onBackground = Color(0xFFF1F1F1),   // Chữ trắng mờ
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
)

// ☀️ Màu sáng – nhẹ nhàng, nền kem, chữ đen
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1976D2),        // Xanh dương tươi
    onPrimary = Color.White,
    secondary = Color(0xFF64B5F6),      // Xanh da trời
    onSecondary = Color.Black,
    background = Color(0xFFFDFDFD),     // Trắng kem
    onBackground = Color(0xFF202124),   // Chữ xám đậm
    surface = Color.White,
    onSurface = Color.Black,
)

@Composable
fun WorkPlanningTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // dùng typography hiện tại của bạn
        content = content
    )
}
