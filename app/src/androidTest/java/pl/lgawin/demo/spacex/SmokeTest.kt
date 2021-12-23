package pl.lgawin.demo.spacex

import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
@LargeTest
class SmokeTest {

    @Test
    fun launchesAndShowsListOfLaunchpads() {
        launchActivity<MainActivity>()
        onView(withText("SpaceX")).check(matches(isDisplayed()))
        TimeUnit.SECONDS.sleep(2)
        onView(withText("Launchpad 1")).check(matches(isDisplayed()))
    }
}
