package dev.gbenga.endurely.ui.theme

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import dev.gbenga.endurely.extensions.asBitmap

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


@ColorInt const val Orange: Long = 0XFFFD9491
@ColorInt const val Purple: Long = 0XFF512DA8
@ColorInt const val Maroon: Long = 0XFFAD1457
//AD1457

//512DA8

object DyColor{
    fun getDominantColor(context: Context, @DrawableRes icRes: Int): Color{

        val newBitmap = Bitmap.createScaledBitmap(icRes.asBitmap(context), 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return Color(color)
    }

    suspend fun getDominantColorInt(context: Context, @DrawableRes icRes: Int): Int{

        val newBitmap = Bitmap.createScaledBitmap(icRes.asBitmap(context), 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
}