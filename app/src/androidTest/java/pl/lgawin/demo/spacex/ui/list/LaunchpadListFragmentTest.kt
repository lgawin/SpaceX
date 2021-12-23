package pl.lgawin.demo.spacex.ui.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.google.common.truth.Truth.assertThat
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
import pl.lgawin.demo.spacex.LOREM_IPSUM_SHORT
import pl.lgawin.demo.spacex.R
import pl.lgawin.demo.spacex.domain.GetAllLaunchpadsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadModel
import pl.lgawin.demo.spacex.ui.list.LaunchpadListFragment

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

    @Test
    fun navigateToDetails() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        launchFragmentInContainer<LaunchpadListFragment>().onFragment {
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        clickListItem(R.id.launchpad_list, 0)
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.launchpadDetailsFragment)
    }
}

class FakeGetAllLaunchpadsUseCase : GetAllLaunchpadsUseCase {

    override suspend fun invoke(): List<LaunchpadModel> = listOf(
        LaunchpadModel("some-id", "Sample Launchpad"),
        LaunchpadModel("another-id",
            "Launchpad with quite very long name that will not fit into one line"),
        LaunchpadModel("yet-another-id", LOREM_IPSUM_SHORT),
    )
}
