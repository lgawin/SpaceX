package pl.lgawin.demo.spacex.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import retrofit2.Retrofit

class SpacexApiTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.NONE)
        modules(retrofitModule)
    }

    val server = MockWebServer()
    val retrofitBuilder by inject<Retrofit.Builder>()
    val retrofit: Retrofit by lazy { retrofitBuilder.baseUrl(server.url("/")).build() }
    val service: SpacexApi by lazy { retrofit.create(SpacexApi::class.java) }

    @Test
    fun `asks for all launchpads`() {
        server.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody("[]")
        })

        runBlocking { service.getAllLaunchPads() }

        with(server.takeRequest()) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo("/v3/launchpads")
        }
    }

    @Test
    fun `retrieves all launchpads`() {
        server.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(launchpadsResponse)
        })

        val launchPads = runBlocking { service.getAllLaunchPads() }

        assertThat(launchPads).hasSize(6)
    }

    @Test
    fun `asks for specific launchpad details`() {
        server.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(launchpadDetailsResponse)
        })

        runBlocking { service.getLaunchpad("some-site-id") }

        with(server.takeRequest()) {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo("/v3/launchpads/some-site-id")
        }
    }

    @Test
    fun `retrieves launchpad details`() {
        server.enqueue(MockResponse().apply {
            setResponseCode(200)
            setBody(launchpadDetailsResponse)
        })

        val launchpad = runBlocking { service.getLaunchpad("some-site-id") }

        assertThat(launchpad).isEqualTo(
            Launchpad(
                1,
                "some-site-id",
                "Launchpad name",
                LaunchpadLocation("some location", "some region", 1.2345, -9.8765),
                "Sample details",
                "active",
            )
        )
    }
}

private val launchpadDetailsResponse = """
    {
        "id": 1, 
        "site_id": "some-site-id", 
        "site_name_long": "Launchpad name",
        "location": {
          "name": "some location",
          "region": "some region",
          "latitude": 1.2345,
          "longitude": -9.8765
        },
        "status": "active",
        "details": "Sample details"
    }
    """.trimIndent()

private val launchpadsResponse = """
    [
      {
        "id": 6,
        "status": "active",
        "location": {
          "name": "Vandenberg Air Force Base",
          "region": "California",
          "latitude": 34.632093,
          "longitude": -120.610829
        },
        "vehicles_launched": [
          "Falcon 9"
        ],
        "attempted_launches": 12,
        "successful_launches": 12,
        "wikipedia": "https://en.wikipedia.org/wiki/Vandenberg_AFB_Space_Launch_Complex_4",
        "details": "SpaceX primary west coast launch pad for polar orbits and sun synchronous orbits, primarily used for Iridium. Also intended to be capable of launching Falcon Heavy.",
        "site_id": "vafb_slc_4e",
        "site_name_long": "Vandenberg Air Force Base Space Launch Complex 4E"
      },
      {
        "id": 1,
        "status": "retired",
        "location": {
          "name": "Omelek Island",
          "region": "Marshall Islands",
          "latitude": 9.0477206,
          "longitude": 167.7431292
        },
        "vehicles_launched": [
          "Falcon 1"
        ],
        "attempted_launches": 5,
        "successful_launches": 2,
        "wikipedia": "https://en.wikipedia.org/wiki/Omelek_Island",
        "details": "SpaceX original launch site, where all of the Falcon 1 launches occured. Abandoned as SpaceX decided against upgrading the pad to support Falcon 9.",
        "site_id": "kwajalein_atoll",
        "site_name_long": "Kwajalein Atoll Omelek Island"
      },
      {
        "id": 2,
        "status": "active",
        "location": {
          "name": "Cape Canaveral",
          "region": "Florida",
          "latitude": 28.5618571,
          "longitude": -80.577366
        },
        "vehicles_launched": [
          "Falcon 9"
        ],
        "attempted_launches": 38,
        "successful_launches": 36,
        "wikipedia": "https://en.wikipedia.org/wiki/Cape_Canaveral_Air_Force_Station_Space_Launch_Complex_40",
        "details": "SpaceX primary Falcon 9 launch pad, where all east coast Falcon 9s launched prior to the AMOS-6 anomaly. Initially used to launch Titan rockets for Lockheed Martin. Back online since CRS-13 on 2017-12-15.",
        "site_id": "ccafs_slc_40",
        "site_name_long": "Cape Canaveral Air Force Station Space Launch Complex 40"
      },
      {
        "id": 8,
        "status": "under construction",
        "location": {
          "name": "Boca Chica Village",
          "region": "Texas",
          "latitude": 25.9972641,
          "longitude": -97.1560845
        },
        "vehicles_launched": [
          "Falcon 9"
        ],
        "attempted_launches": 0,
        "successful_launches": 0,
        "wikipedia": "https://en.wikipedia.org/wiki/SpaceX_South_Texas_Launch_Site",
        "details": "SpaceX new launch site currently under construction to help keep up with the Falcon 9 and Heavy manifests. Expected to be completed in late 2018. Initially will be limited to 12 flights per year, and only GTO launches.",
        "site_id": "stls",
        "site_name_long": "SpaceX South Texas Launch Site"
      },
      {
        "id": 4,
        "status": "active",
        "location": {
          "name": "Cape Canaveral",
          "region": "Florida",
          "latitude": 28.6080585,
          "longitude": -80.6039558
        },
        "vehicles_launched": [
          "Falcon 9",
          "Falcon Heavy"
        ],
        "attempted_launches": 14,
        "successful_launches": 14,
        "wikipedia": "https://en.wikipedia.org/wiki/Kennedy_Space_Center_Launch_Complex_39#Launch_Pad_39A",
        "details": "NASA historic launch pad that launched most of the Saturn V and Space Shuttle missions. Initially for Falcon Heavy launches, it is now launching all of SpaceX east coast missions due to the damage from the AMOS-6 anomaly. After SLC-40 repairs are complete, it will be upgraded to support Falcon Heavy, a process which will take about two months. In the future it will launch commercial crew missions and the Interplanetary Transport System.",
        "site_id": "ksc_lc_39a",
        "site_name_long": "Kennedy Space Center Historic Launch Complex 39A"
      },
      {
        "id": 5,
        "status": "retired",
        "location": {
          "name": "Vandenberg Air Force Base",
          "region": "California",
          "latitude": 34.6440904,
          "longitude": -120.5931438
        },
        "vehicles_launched": [
          "Falcon 1"
        ],
        "attempted_launches": 0,
        "successful_launches": 0,
        "wikipedia": "https://en.wikipedia.org/wiki/Vandenberg_AFB_Space_Launch_Complex_3",
        "details": "SpaceX original west coast launch pad for Falcon 1. Performed a static fire but was never used for a launch and abandoned due to scheduling conflicts.",
        "site_id": "vafb_slc_3w",
        "site_name_long": "Vandenberg Air Force Base Space Launch Complex 3W"
      }
    ]
""".trimIndent()
