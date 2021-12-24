package pl.lgawin.demo.spacex

import android.location.Location
import android.location.Location.FORMAT_SECONDS
import pl.lgawin.demo.spacex.ui.details.CoordinatesConverter
import kotlin.math.abs
import kotlin.math.round

class AndroidLocationCoordinatesConverter : CoordinatesConverter {

    override fun convert(latitude: Double, longitude: Double): String {
        val latF = formatCoordinate(latitude)
        val latDir = if (latitude < 0.0) "S" else "N"
        val lngF = formatCoordinate(longitude)
        val lngDir = if (longitude < 0.0) "W" else "E"
        return "$latF$latDir $lngF$lngDir"
    }

    private fun formatCoordinate(latitude: Double): String {
        val lat =
            Location.convert(abs(latitude), FORMAT_SECONDS).split(":").dropLastWhile { it == "0" }
                .ifEmpty { listOf("0") }
        val components = mapOf(
            lat.getOrNull(0) to "°",
            lat.getOrNull(1)?.let { (it.toInt() + 100).toString().drop(1) } to "'",
            lat.getOrNull(2)?.toDouble()?.round(1) to "\"",
        ).filterKeys { it != null }
        return components.entries.joinToString(separator = "") { (k, v) -> "$k$v" }
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}
