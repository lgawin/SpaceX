package pl.lgawin.demo.spacex

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class SmokeTest : ScreenshotTest {

    @Test
    fun launchAndShowListOfLaunchpads() {
        val activity = launchActivity<MainActivity>().waitForActivity()
        assertDisplayed("SpaceX")
        sleep(2, TimeUnit.SECONDS) // FIXME - hardcoded sleep will fail when real e2e test will be applied
        assertDisplayed("Launchpad 1")
        compareScreenshot(activity, name = "launchpad_list_initial")
        scrollListToPosition(R.id.launchpad_list, 40)
        assertDisplayed("Launchpad 40")
        compareScreenshot(activity, name = "launchpad_list_scrolled")
    }
}
