package pl.lgawin.demo.spacex.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.dsl.module
import pl.lgawin.demo.spacex.GetAllLaunchpadsUseCase
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
    factory<GetAllLaunchpadsUseCase> {
        object : GetAllLaunchpadsUseCase {
            override suspend fun invoke(): List<LaunchpadModel> {
                val retrofit: Retrofit = get<Retrofit.Builder>()
                    .baseUrl("https://api.spacexdata.com/")
                    .build()
                val service = retrofit.create(SpacexApi::class.java)
                return service.getAllLaunchPads().map {
                    LaunchpadModel(it.siteId, it.siteName)
                }
            }
        }
    }
} + retrofitModule
