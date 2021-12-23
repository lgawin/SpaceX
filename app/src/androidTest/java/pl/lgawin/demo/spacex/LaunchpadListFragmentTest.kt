package pl.lgawin.demo.spacex

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.karumi.shot.FragmentScenarioUtils.waitForFragment
import com.karumi.shot.ScreenshotTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.domain.LaunchpadModel

@RunWith(AndroidJUnit4::class)
class LaunchpadListFragmentTest : ScreenshotTest, KoinTest {

    private val mockModule = module {
        single { FakeGetAllLaunchpadsUseCase() } bind GetAllLaunchpadsUseCase::class
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
        val fragment =
            launchFragmentInContainer<LaunchpadListFragment>(themeResId = R.style.Theme_SpaceX)
                .waitForFragment()
        assertDisplayed("Sample Launchpad")
        compareScreenshot(fragment)
    }
}

class FakeGetAllLaunchpadsUseCase : GetAllLaunchpadsUseCase {

    override suspend fun invoke(): List<LaunchpadModel> = listOf(
        LaunchpadModel("some-id", "Sample Launchpad"),
        LaunchpadModel("another-id", "Launchpad with quite very long name that will not fit into one line"),
        LaunchpadModel("yet-another-id", LOREM_IPSUM_SHORT),
    )
}
