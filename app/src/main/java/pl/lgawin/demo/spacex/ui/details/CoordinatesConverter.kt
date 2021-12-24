package pl.lgawin.demo.spacex.ui.details

interface CoordinatesConverter {
    fun convert(latitude: Double, longitude: Double): String

}
