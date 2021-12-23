package pl.lgawin.demo.spacex

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickBack
import com.adevinta.android.barista.interaction.BaristaClickInteractions.clickOn
import com.adevinta.android.barista.interaction.BaristaListInteractions.clickListItem
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.mock.getAllLaunchpadsMock
import pl.lgawin.demo.spacex.ui.MainActivity
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class SmokeTest : ScreenshotTest, KoinTest {

    @Before
    fun before() {
        loadKoinModules(getAllLaunchpadsMock)
    }

    @After
    fun after() {
        unloadKoinModules(getAllLaunchpadsMock)
    }

    @Test
    fun launchAndShowListOfLaunchpads() {
        val activity = launchActivity<MainActivity>().waitForActivity()
        assertDisplayed("SpaceX")
        assertDisplayed("Launchpad 1")
        compareScreenshot(activity, name = "launchpad_list_initial")
        scrollListToPosition(R.id.launchpad_list, 40)
        assertDisplayed("Launchpad 40")
        compareScreenshot(activity, name = "launchpad_list_scrolled")
        clickListItem(R.id.launchpad_list, 35)
        assertDisplayed("launchpad 36 details")
        compareScreenshot(activity, name = "launchpad_details")
        clickBack()
        clickOn("Launchpad 39")
        assertDisplayed("launchpad 39 details")
        clickBack()
        sleep(2, TimeUnit.SECONDS) // TODO check if back on the list
    }
}
