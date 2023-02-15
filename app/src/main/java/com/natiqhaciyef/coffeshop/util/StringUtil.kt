package com.natiqhaciyef.coffeshop.util

import java.text.SimpleDateFormat
import java.util.*

fun String.setFirstIndexUpper(): String{
    return "${this[0].uppercase()}${this.removeRange(0..0)}"
}

fun calendarFormatter(calendar: Calendar): String {
    val format = "dd-MM-yyyy HH:mm"
    val sdf = SimpleDateFormat(format, Locale.UK)
    return sdf.format(calendar.time)
}

