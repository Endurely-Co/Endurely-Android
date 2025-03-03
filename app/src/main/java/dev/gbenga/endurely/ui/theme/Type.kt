package dev.gbenga.endurely.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with

@Composable
fun TypographyConfig(darkTheme: Boolean): Typography{

    val textColor = when{
        darkTheme -> Color.White.copy(alpha = .8f)
        else -> Color.Black.copy(alpha = .8f)
    }


    return Typography(
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
            color = textColor
        ),

        headlineSmall =MaterialTheme.typography.headlineSmall.copy(color = textColor),
        headlineMedium =MaterialTheme.typography.headlineMedium.copy(color = textColor),
        bodySmall = MaterialTheme.typography.bodySmall.copy(color = textColor),
        bodyMedium = MaterialTheme.typography.bodyMedium.copy(color = textColor),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
            color = textColor
        ),
        labelSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
            color = textColor
        )
        /* Other default text styles to override

        */
    )
}