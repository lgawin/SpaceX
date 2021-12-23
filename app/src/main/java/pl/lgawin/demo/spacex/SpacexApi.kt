package pl.lgawin.demo.spacex

import retrofit2.http.GET

interface SpacexApi {

    @GET("/v3/launchpads")
    suspend fun getAllLaunchPads(): List<Launchpad>
}

data class Launchpad(val id: Int)
