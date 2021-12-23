package pl.lgawin.demo.spacex.domain

interface GetAllLaunchpadsUseCase {
    suspend operator fun invoke(): List<LaunchpadModel>
}
