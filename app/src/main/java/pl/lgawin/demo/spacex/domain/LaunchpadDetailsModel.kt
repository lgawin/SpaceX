package pl.lgawin.demo.spacex.domain

data class LaunchpadDetailsModel(
    val id: String,
    val name: String,
    val description: String,
    val status: String,
    val location: LaunchpadLocationModel,
)
