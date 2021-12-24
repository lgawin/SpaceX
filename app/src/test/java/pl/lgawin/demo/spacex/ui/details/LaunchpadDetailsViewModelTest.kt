package pl.lgawin.demo.spacex.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel
import pl.lgawin.demo.spacex.ui.details.LaunchpadDetailsViewModel.*

@ExperimentalCoroutinesApi
class LaunchpadDetailsViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `exposes correct ui representation`() {
        val detailsFlow = MutableSharedFlow<LaunchpadDetailsModel>(replay = 1)
        val fakeGetLaunchpadDetailsUseCase = object : GetLaunchpadDetailsUseCase {
            override suspend fun invoke(id: String) = detailsFlow.first()
        }
        val launchpadDetailsModel = LaunchpadDetailsModel("some-id",
            "updated-name",
            "description",
            "status",
            LaunchpadLocationModel(
                latitude = 34.6440904,
                longitude = -120.5931438,
                "Vandenberg Air Force Base/California"
            )
        )

        val coordinatesConverter = mockk<CoordinatesConverter> {
            every { convert(any(), any()) } returns """34°38'38.7"N 120°35'35.3"W"""
        }
        val viewModel = LaunchpadDetailsViewModel(
            "some-id",
            "some-name",
            fakeGetLaunchpadDetailsUseCase,
            coordinatesConverter,
        ).also {
            it.details.observeForever {}
        }

        assertThat(viewModel.details.value).isEqualTo(LaunchpadDetailsUiModel("some-name"))

        detailsFlow.tryEmit(launchpadDetailsModel)

        assertThat(viewModel.details.value)
            .isEqualTo(
                LaunchpadDetailsUiModel(
                    "updated-name",
                    "description",
                    "status",
                    """Vandenberg Air Force Base/California
                |34°38'38.7"N 120°35'35.3"W
            """.trimMargin()
                )
            )
    }
}
