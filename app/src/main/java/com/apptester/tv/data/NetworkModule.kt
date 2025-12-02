package com.apptester.tv.data

import com.apptester.tv.data.network.api.ApiFirebaseDistribution
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import org.koin.core.qualifier.named
import org.koin.dsl.module


val networkModule = module {

    factory { ApiFirebaseDistribution(get(named("appdistributionClient"))) }

    single(named("appdistributionClient")) {
        HttpClient(OkHttp) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                host = "https://firebaseappdistribution.googleapis.com"
            }
        }
    }

}
