package pl.lgawin.demo.spacex.api

import retrofit2.http.GET
import retrofit2.http.Path

interface SpacexApi {

    @GET("/v3/launchpads")
    suspend fun getAllLaunchPads(): List<Launchpad>

    @GET("/v3/launchpads/{site_id}")
    suspend fun getLaunchpad(@Path("site_id") siteId: String): Launchpad
}

