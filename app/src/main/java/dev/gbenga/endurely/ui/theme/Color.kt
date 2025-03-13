package dev.gbenga.endurely.ui.theme

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import dev.gbenga.endurely.extensions.asBitmap

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


@ColorInt const val Orange: Long = 0XFFFD9491
@ColorInt const val FitnessBlue: Long = 0XFFE53935
@ColorInt const val Purple: Long = 0XFF8E24AA //1565C0
@ColorInt const val Maroon: Long = 0XFFD84315
@ColorInt const val SnackBarDark: Long = 0XFF006064 //B2EBF2
@ColorInt const val SnackBarLight: Long = 0XFFB2EBF2 //B2EBF2
//006064
//AD1457

//512DA8

data class AppColor(val defaultCard: Color = Color.White,
                    val snackBg: Color = Color(SnackBarDark),
    val routineMainImageBg: Color = Color.Gray )

@Composable
fun appColor(darkTheme: Boolean = isSystemInDarkTheme()): AppColor{
    return remember {
        when {
            darkTheme -> AppColor(defaultCard = Color(0xFF212121),
                snackBg = Color(SnackBarDark), routineMainImageBg = Color(0xffB3E5FC))
            else -> AppColor(defaultCard = Color(0XFFECF0F1),
                snackBg = Color(SnackBarLight  ),
                routineMainImageBg = Color(0xFF2196F3)
            )
        }
    }
}

