package com.gxsales.client.model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class Extensions {
    companion object{
        fun Int.dpToPx(context: Context): Int {
            val scale = context.resources.displayMetrics.density
            return (this * scale + 0.5f).toInt()
        }
        fun String.formatDate(): String {
            val inputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
            val date = inputFormat.parse(this)
            return outputFormat.format(date!!)
        }
    }

}