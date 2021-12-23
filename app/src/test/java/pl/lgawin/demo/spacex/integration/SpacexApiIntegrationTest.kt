package pl.lgawin.demo.spacex.integration

import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Test
import pl.lgawin.demo.spacex.SpacexApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class SpacexApiIntegrationTest {

    val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.spacexdata.com/")
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        )
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Test
    fun `gets all launchpads`() {
        val service = retrofit.create(SpacexApi::class.java)

        val launchPads = runBlocking { service.getAllLaunchPads() }
        assertThat(launchPads).hasSize(6)
    }
}
