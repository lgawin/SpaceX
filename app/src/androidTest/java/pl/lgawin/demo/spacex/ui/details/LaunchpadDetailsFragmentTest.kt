package pl.lgawin.demo.spacex.ui.details

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import com.karumi.shot.ScreenshotTest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel
import pl.lgawin.demo.spacex.ui.list.LaunchpadListFragmentDirections

@RunWith(AndroidJUnit4::class)
class LaunchpadDetailsFragmentTest : ScreenshotTest, KoinTest {

    private val detailsFlow = MutableSharedFlow<LaunchpadDetailsModel>(replay = 1)

    private val mockModule = module {
        single {
            object : GetLaunchpadDetailsUseCase {
                override suspend fun invoke(id: String) = detailsFlow.first()
            }
        } bind GetLaunchpadDetailsUseCase::class
    }

    @Before
    fun setUp() {
        loadKoinModules(mockModule)
    }

    @After
    fun tearDown() {
        unloadKoinModules(mockModule)
    }

    @Test
    fun launch() {
        val launchpadDetails = LaunchpadDetailsModel(
            "some-id",
            "name",
            "Launchpad description",
            "",
            LaunchpadLocationModel(0.0, 0.0, "")
        )
        val fragment = launchFragmentInContainer<LaunchpadDetailsFragment>(
            themeResId = R.style.Theme_SpaceX,
            fragmentArgs = LaunchpadListFragmentDirections.actionLaunchpadListToLaunchpadDetails("some-id").arguments
        ).waitForFragment()
        assertDisplayed("name: some-id")
        compareScreenshot(fragment)
        // TODO check that view is loading data
        detailsFlow.tryEmit(launchpadDetails)
        assertDisplayed("description: Launchpad description")
        compareScreenshot(fragment, name = "data_loaded")
    }
}
