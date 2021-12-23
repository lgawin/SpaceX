package pl.lgawin.demo.spacex

import pl.lgawin.demo.spacex.domain.LaunchpadModel

interface GetAllLaunchpadsUseCase {
    suspend operator fun invoke(): List<LaunchpadModel>
}
