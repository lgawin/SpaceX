package pl.lgawin.demo.spacex.ui.details

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import com.karumi.shot.ScreenshotTest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.LOREM_IPSUM_MULTI_PARAGRAPH
import pl.lgawin.demo.spacex.LOREM_IPSUM_SHORT
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel
import pl.lgawin.demo.spacex.ui.list.LaunchpadListFragmentDirections
import java.util.concurrent.TimeUnit

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
            "Launchpad name",
            "Launchpad description",
            "under construction",
            LaunchpadLocationModel(28.5618571, -80.577366, "Cape Canaveral/Florida")
        )
        val fragment = launchFragmentInContainer<LaunchpadDetailsFragment>(
            themeResId = R.style.Theme_SpaceX,
            fragmentArgs = LaunchpadListFragmentDirections.actionLaunchpadListToLaunchpadDetails("some-id", "Launchpad name").arguments
        ).waitForFragment()
        assertDisplayed("Launchpad name")
        assertNotDisplayed("status:")
        assertNotDisplayed("location:")
        compareScreenshot(fragment)
        // TODO check that view is loading data
        detailsFlow.tryEmit(launchpadDetails)
        assertDisplayed("Launchpad name")
        assertDisplayed("Launchpad description")
        assertDisplayed("status:")
        assertDisplayed("under construction")
        assertDisplayed("location:")
        assertDisplayed("""Cape Canaveral/Florida
            |28°33'42.7"N 80°34'38.5"W""".trimMargin())
        compareScreenshot(fragment, name = "data_loaded")
    }

    @Test
    fun extremelyLong() {
        val launchpadDetails = LaunchpadDetailsModel(
            "some-id",
            LOREM_IPSUM_SHORT,
            LOREM_IPSUM_MULTI_PARAGRAPH,
            "retired",
            LaunchpadLocationModel(28.5618571, -80.577366, "Cape Canaveral/Florida")
        )
        val fragment = launchFragmentInContainer<LaunchpadDetailsFragment>(
            themeResId = R.style.Theme_SpaceX,
            fragmentArgs = LaunchpadListFragmentDirections.actionLaunchpadListToLaunchpadDetails("some-id", "Launchpad name").arguments
        ).waitForFragment()
        detailsFlow.tryEmit(launchpadDetails)
        assertDisplayed(LOREM_IPSUM_SHORT)
        assertDisplayed("retired")
        assertDisplayed("""Cape Canaveral/Florida
            |28°33'42.7"N 80°34'38.5"W""".trimMargin())
        compareScreenshot(fragment, name = "extremely_long")
    }
}
