package pl.lgawin.demo.spacex.domain

interface GetLaunchpadDetailsUseCase {
    suspend operator fun invoke(id: String): LaunchpadDetailsModel
}
