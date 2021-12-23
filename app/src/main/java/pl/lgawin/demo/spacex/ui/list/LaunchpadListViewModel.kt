package pl.lgawin.demo.spacex.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import pl.lgawin.demo.spacex.domain.GetAllLaunchpadsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadModel

class LaunchpadListViewModel(
    private val getAllLaunchpadsUseCase: GetAllLaunchpadsUseCase,
) : ViewModel() {

    val launchpads: LiveData<List<LaunchpadModel>> = liveData { emit(getAllLaunchpadsUseCase()) }
}
