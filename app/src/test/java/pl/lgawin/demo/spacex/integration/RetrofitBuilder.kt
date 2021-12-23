package pl.lgawin.demo.spacex.integration

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun retrofitBuilder(baseUrl: String): Retrofit.Builder = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(
            Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
        )
    )
    .baseUrl(baseUrl)
