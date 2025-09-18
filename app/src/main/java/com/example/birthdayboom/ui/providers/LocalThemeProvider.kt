package com.example.birthdayboom.ui.providers

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColorScheme(
    val backgroundColor: Color,
    val surfaceColor: Color,
    val cardColor: Color,

    val primaryColor: Color,
    val onPrimaryColor: Color,
    val secondaryColor: Color,
    val onSecondaryColor: Color,

    val textPrimaryColor: Color,
    val textSecondaryColor: Color,
    val textDisabledColor: Color,

    val buttonColor: Color,
    val onButtonColor: Color,

    val errorColor: Color,
    val successColor: Color,
    val warningColor: Color,

    val dividerColor: Color,
    val borderColor: Color,
    val iconColor: Color,
)

val lightThemeColor = AppColorScheme(
    backgroundColor = Color(0xFFFFFFFF),
    surfaceColor = Color(0xFFF5F5F5),
    cardColor = Color(0xFFFFFFFF),

    primaryColor = Color(0xFF6200EE),
    onPrimaryColor = Color(0xFFFFFFFF),
    secondaryColor = Color(0xFF03DAC6),
    onSecondaryColor = Color(0xFF000000),

    textPrimaryColor = Color(0xFF000000),
    textSecondaryColor = Color(0xFF444444),
    textDisabledColor = Color(0xFF888888),

    buttonColor = Color(0xFF6200EE),
    onButtonColor = Color(0xFFFFFFFF),

    errorColor = Color(0xFFB00020),
    successColor = Color(0xFF388E3C),
    warningColor = Color(0xFFF57C00),

    dividerColor = Color(0xFFE0E0E0),
    borderColor = Color(0xFFBDBDBD),
    iconColor = Color(0xFF333333),
)

val darkThemeColor = AppColorScheme(
    backgroundColor = Color(0xFF121212),
    surfaceColor = Color(0xFF1E1E1E),
    cardColor = Color(0xFF1E1E1E),

    primaryColor = Color(0xFFBB86FC),
    onPrimaryColor = Color(0xFF000000),
    secondaryColor = Color(0xFF03DAC6),
    onSecondaryColor = Color(0xFF000000),

    textPrimaryColor = Color(0xFFFFFFFF),
    textSecondaryColor = Color(0xFFBBBBBB),
    textDisabledColor = Color(0xFF777777),

    buttonColor = Color(0xFFBB86FC),
    onButtonColor = Color(0xFF000000),

    errorColor = Color(0xFFCF6679),
    successColor = Color(0xFF4CAF50),
    warningColor = Color(0xFFFFA726),

    dividerColor = Color(0xFF2C2C2C),
    borderColor = Color(0xFF444444),
    iconColor = Color(0xFFFFFFFF),
)

val LocalThemeProvider = staticCompositionLocalOf<AppColorScheme> { lightThemeColor }
