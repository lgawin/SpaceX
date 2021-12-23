package pl.lgawin.demo.spacex.ui.details

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
import org.koin.dsl.module
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.ui.list.LaunchpadListFragmentDirections

@RunWith(AndroidJUnit4::class)
class LaunchpadDetailsFragmentTest : ScreenshotTest, KoinTest {

    private val mockModule = module {
//        single { FakeGetAllLaunchpadsUseCase() } bind GetAllLaunchpadsUseCase::class
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
            launchFragmentInContainer<LaunchpadDetailsFragment>(
                themeResId = R.style.Theme_SpaceX,
                fragmentArgs = LaunchpadListFragmentDirections.actionLaunchpadListToLaunchpadDetails("some-id").arguments
            )
                .waitForFragment()
        assertDisplayed("launchpad some-id details")
        compareScreenshot(fragment)
    }
}
