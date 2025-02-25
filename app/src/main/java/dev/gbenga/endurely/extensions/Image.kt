package dev.gbenga.endurely.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun Int.asBitmap(context: Context): Bitmap = BitmapFactory.decodeResource(context.resources, this)