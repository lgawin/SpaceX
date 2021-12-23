package pl.lgawin.demo.spacex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase

class LaunchpadDetailsViewModel(
    private val launchpadId: String,
    private val getLaunchpadDetailsUseCase: GetLaunchpadDetailsUseCase,
) : ViewModel() {

    val name = liveData {
        emit("name: $launchpadId")
    }

    val description = liveData {
        val details = getLaunchpadDetailsUseCase(launchpadId)
        emit("description: ${details.description}")
    }
}
