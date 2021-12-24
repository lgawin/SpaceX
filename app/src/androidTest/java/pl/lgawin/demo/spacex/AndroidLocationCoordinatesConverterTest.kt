package pl.lgawin.demo.spacex

import com.google.common.truth.Truth.assertThat
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class AndroidLocationCoordinatesConverterTest {

    val converter = AndroidLocationCoordinatesConverter()

    @Test
    @Parameters(value = [
        """0.0,0.0,0°N 0°E""",
        """34.6440904,-120.5931438,34°38'38.7"N 120°35'35.3"W""",
        """25.9972641,-97.1560845,25°59'50.2"N 97°09'21.9"W""",
        """28.5618571,-80.577366,28°33'42.7"N 80°34'38.5"W""",
        """-1.0, 1.0,1°S 1°E""",
        """-1.2345, 1.2345,1°14'4.2"S 1°14'4.2"E""",
    ])
    fun converts(lat: Double, lng: Double, expected: String) {
        assertThat(converter.convert(lat, lng)).isEqualTo(expected)
    }
}

