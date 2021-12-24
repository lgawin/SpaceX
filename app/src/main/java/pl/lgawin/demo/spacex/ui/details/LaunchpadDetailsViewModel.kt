package pl.lgawin.demo.spacex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel

class LaunchpadDetailsViewModel(
    private val launchpadId: String,
    private val launchpadName: String,
    private val getLaunchpadDetailsUseCase: GetLaunchpadDetailsUseCase,
    private val coordinatesConverter: CoordinatesConverter,
) : ViewModel() {

    val details = liveData {
        emit(LaunchpadDetailsUiModel(launchpadName))
        val details = getLaunchpadDetailsUseCase(launchpadId)
        emit(details.mapToUiModel())
    }

    private fun formatLocation(location: LaunchpadLocationModel) =
        """${location.description}
            |${coordinatesConverter.convert(location.latitude, location.longitude)}""".trimMargin()

    private fun LaunchpadDetailsModel.mapToUiModel() =
        LaunchpadDetailsUiModel(name, description, status, formatLocation(location))

    data class LaunchpadDetailsUiModel(
        val name: String,
        val description: String? = null,
        val status: String? = null,
        val location: String? = null,
    )
}
