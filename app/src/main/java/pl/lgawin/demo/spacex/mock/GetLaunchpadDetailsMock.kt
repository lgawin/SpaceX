package pl.lgawin.demo.spacex.mock

import org.koin.dsl.bind
import org.koin.dsl.module
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel

val getLaunchpadDetailsMock = module {
    factory { GetLaunchpadDetails() } bind GetLaunchpadDetailsUseCase::class
}

private class GetLaunchpadDetails : GetLaunchpadDetailsUseCase {

    override suspend fun invoke(id: String) = LaunchpadDetailsModel(
        id,
        "name",
        "Launchpad $id description",
        "status",
        LaunchpadLocationModel(0.0, 0.0, "some location")
    )
}
