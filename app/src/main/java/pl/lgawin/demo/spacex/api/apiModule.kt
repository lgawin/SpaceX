package pl.lgawin.demo.spacex.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import pl.lgawin.demo.spacex.domain.GetAllLaunchpadsUseCase
import pl.lgawin.demo.spacex.domain.GetLaunchpadDetailsUseCase
import pl.lgawin.demo.spacex.domain.LaunchpadDetailsModel
import pl.lgawin.demo.spacex.domain.LaunchpadLocationModel
import pl.lgawin.demo.spacex.domain.LaunchpadModel
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val retrofitModule = module {
    factory<Retrofit.Builder> {
        Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .addLast(KotlinJsonAdapterFactory())
                        .build()
                )
            )
    }
}

val apiModule = module {
    single {
        get<Retrofit.Builder>()
            .baseUrl("https://api.spacexdata.com/")
            .build()
    }
    factory { get<Retrofit>().create(SpacexApi::class.java) }

    factory<GetAllLaunchpadsUseCase> {
        object : GetAllLaunchpadsUseCase {
            override suspend fun invoke(): List<LaunchpadModel> {
                val service = get<SpacexApi>()
                return service.getAllLaunchPads().map {
                    LaunchpadModel(it.siteId, it.siteName)
                }
            }
        }
    }

    factory<GetLaunchpadDetailsUseCase> {
        object : GetLaunchpadDetailsUseCase {
            override suspend fun invoke(id: String): LaunchpadDetailsModel {
                val service = get<SpacexApi>()
                val launchpad = service.getLaunchpad(id)
                return with(launchpad) {
                    LaunchpadDetailsModel(
                        id = siteId,
                        siteName,
                        details,
                        status,
                        LaunchpadLocationModel(
                            location.latitude,
                            location.longitude,
                            "${location.name}/${location.region}"
                        )
                    )
                }
            }
        }
    }
} + retrofitModule
