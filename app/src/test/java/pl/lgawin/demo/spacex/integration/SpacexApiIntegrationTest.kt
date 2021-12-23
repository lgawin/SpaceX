package pl.lgawin.demo.spacex.integration

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import pl.lgawin.demo.spacex.api.SpacexApi
import retrofit2.Retrofit

internal class SpacexApiIntegrationTest {

    val retrofit: Retrofit = retrofitBuilder("https://api.spacexdata.com/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .build()

    @Test
    fun `gets all launchpads`() {
        val service = retrofit.create(SpacexApi::class.java)

        val launchPads = runBlocking { service.getAllLaunchPads() }

        assertThat(launchPads).hasSize(6)
    }

    @Test
    fun `gets launchpad details`() {
        val service = retrofit.create(SpacexApi::class.java)
        val allLaunchPads = runBlocking { service.getAllLaunchPads() }
        val firstLaunchpad = allLaunchPads[0]

        val launchPad = runBlocking { service.getLaunchpad(firstLaunchpad.siteId) }

        assertThat(launchPad).isEqualTo(firstLaunchpad)
    }
}
