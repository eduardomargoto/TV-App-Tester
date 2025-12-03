package com.apptester.tv.data

import com.apptester.tv.data.network.api.ApiAppDistribution
import com.apptester.tv.data.network.api.ApiFirebase
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val APP_DISTRIBUTION_CLIENT = "appdistributionClient"
private const val FIREBASE_CLIENT = "firebaseClient"

val networkModule = module {

    factory {
        ApiAppDistribution(get(named(APP_DISTRIBUTION_CLIENT)))
    }

    factory {
        ApiFirebase(get(named(FIREBASE_CLIENT)))
    }

    single(named(APP_DISTRIBUTION_CLIENT)) {
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

    single(named(FIREBASE_CLIENT)) {
        HttpClient(OkHttp) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                host = "https://firebase.googleapis.com/v1beta1"
            }
        }
    }

}
