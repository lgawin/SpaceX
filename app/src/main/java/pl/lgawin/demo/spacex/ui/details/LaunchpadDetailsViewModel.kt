package pl.lgawin.demo.spacex.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData

class LaunchpadDetailsViewModel(private val launchpadId: String) : ViewModel() {

    val name = liveData {
        emit("launchpad $launchpadId details")
    }
}
