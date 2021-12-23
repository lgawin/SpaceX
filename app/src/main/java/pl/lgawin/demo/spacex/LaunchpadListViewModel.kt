package pl.lgawin.demo.spacex

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.delay
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadListViewModel : ViewModel() {

    val launchpads: LiveData<List<LaunchpadModel>> = liveData {
        // FIXME load real data
        delay(2_000)
        emit(
            (1..100).map { LaunchpadModel(it.toString(), "Launchpad $it") }
        )
    }
}
