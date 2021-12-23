package pl.lgawin.demo.spacex.mock

import kotlinx.coroutines.delay
import org.koin.dsl.bind
import org.koin.dsl.module
import pl.lgawin.demo.spacex.GetAllLaunchpadsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadModel

val getAllLaunchpadsMock = module {
    factory { GetAllLaunchpadsMock() } bind GetAllLaunchpadsUseCase::class

}

private class GetAllLaunchpadsMock : GetAllLaunchpadsUseCase {

    override suspend operator fun invoke(): List<LaunchpadModel> {
        // FIXME load real data
        delay(2_000)
        return (1..100).map { LaunchpadModel(it.toString(), "Launchpad $it") }
    }
}
