package pl.lgawin.demo.spacex

import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions
import com.adevinta.android.barista.interaction.BaristaSleepInteractions.sleep
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class SmokeTest {

    @Test
    fun launchAndShowListOfLaunchpads() {
        launchActivity<MainActivity>()
        assertDisplayed("SpaceX")
        sleep(2, TimeUnit.SECONDS) // FIXME - hardcoded sleep will fail when real e2e test will be applied
        assertDisplayed("Launchpad 1")
        BaristaListInteractions.scrollListToPosition(R.id.launchpad_list, 40)
        assertDisplayed("Launchpad 40")
    }
}
