package pl.lgawin.demo.spacex

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import com.karumi.shot.ActivityScenarioUtils.waitForActivity
import com.karumi.shot.ScreenshotTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import pl.lgawin.demo.spacex.api.apiModule
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class EndToEndTest : ScreenshotTest, KoinTest {

    @Before
    fun before() {
        loadKoinModules(apiModule)
    }

    @After
    fun after() {
    }

    @Test
    fun launchAndShowListOfLaunchpads() {
        val activity = launchActivity<MainActivity>().waitForActivity()
        assertDisplayed("SpaceX")
        sleep(2, TimeUnit.SECONDS) // FIXME - can be flacky...
        assertDisplayed("SpaceX South Texas Launch Site")
        compareScreenshot(activity)
    }
}
