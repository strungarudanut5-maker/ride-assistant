package com.rideassistant.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Extensii Kotlin pentru funcții comune
 */

/**
 * Convertește timestamp în dată formatată
 */
fun Long.toFormattedDate(pattern: String = "dd/MM/yyyy HH:mm"): String {
    val sdf = SimpleDateFormat(pattern, Locale("ro", "RO"))
    return sdf.format(Date(this))
}

/**
 * Formează valuta RON
 */
fun Double.toRON(): String = String.format("%.2f RON", this)

/**
 * Formează distanța în km
 */
fun Double.toKmFormat(): String = String.format("%.1f km", this)

/**
 * Formează tempo RON/km
 */
fun Double.toRonPerKm(): String = String.format("%.2f RON/km", this)

/**
 * Formează tempo RON/oră
 */
fun Double.toRonPerHour(): String = String.format("%.2f RON/h", this)

/**
 * Convertește minute în format HH:MM
 */
fun Int.toTimeFormat(): String {
    val hours = this / 60
    val minutes = this % 60
    return String.format("%02d:%02d", hours, minutes)
}

/**
 * Calculează diferența de timp de la acum
 */
fun Long.getTimeAgo(): String {
    val now = System.currentTimeMillis()
    val diff = now - this
    
    return when {
        diff < 60000 -> "acum"
        diff < 3600000 -> "${diff / 60000} min"
        diff < 86400000 -> "${diff / 3600000} ore"
        diff < 604800000 -> "${diff / 86400000} zile"
        else -> "${diff / 604800000} săptămâni"
    }
}
