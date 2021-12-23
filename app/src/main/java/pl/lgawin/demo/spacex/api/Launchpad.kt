package pl.lgawin.demo.spacex.api

import com.squareup.moshi.Json

data class Launchpad(
    val id: Int,
    @Json(name = "site_id") val siteId: String,
    @Json(name = "site_name_long") val siteName: String,
    val location: LaunchpadLocation,
    val details: String,
    val status: String,
)
