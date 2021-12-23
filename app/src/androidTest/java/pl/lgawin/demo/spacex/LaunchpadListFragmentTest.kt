package pl.lgawin.demo.spacex

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.domain.LaunchpadModel

@RunWith(AndroidJUnit4::class)
class LaunchpadListFragmentTest : KoinTest {

    @Test
    fun launch() {
        loadKoinModules(module {
            single { FakeGetAllLaunchpadsUseCase() } bind GetAllLaunchpadsUseCase::class
        })

        launchFragmentInContainer<LaunchpadListFragment>(themeResId = R.style.Theme_SpaceX)

        assertDisplayed("Sample Launchpad")
    }
}

class FakeGetAllLaunchpadsUseCase : GetAllLaunchpadsUseCase {

    override suspend fun invoke(): List<LaunchpadModel> = listOf(
        LaunchpadModel("some-id", "Sample Launchpad"),
        LaunchpadModel("another-id", "Launchpad with quite very long name that will not fit into one line"),
        LaunchpadModel("yet-another-id", LOREM_IPSUM_SHORT),
    )
}
