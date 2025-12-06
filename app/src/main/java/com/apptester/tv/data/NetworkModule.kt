package com.apptester.tv.data

import com.apptester.tv.data.network.api.ApiAppDistribution
import com.apptester.tv.data.network.api.ApiFirebase
import com.apptester.tv.data.network.api.ApiGoogleOAuth2
import com.apptester.tv.domain.repository.OAuth2Repository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val APP_DISTRIBUTION_CLIENT = "appdistributionClient"
private const val FIREBASE_CLIENT = "firebaseClient"
private const val GOOGLE_OAUTH2_CLIENT = "googleoauth2Client"

val networkModule = module {

    factory {
        ApiAppDistribution(get(named(APP_DISTRIBUTION_CLIENT)))
    }

    factory {
        ApiFirebase(get(named(FIREBASE_CLIENT)))
    }

    factory {
        ApiGoogleOAuth2(get(named(GOOGLE_OAUTH2_CLIENT)))
    }

    single(named(APP_DISTRIBUTION_CLIENT)) {
        val oAuth2Repository = get<OAuth2Repository>()
        HttpClient(OkHttp) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url.protocol = URLProtocol.HTTPS
                host = "firebaseappdistribution.googleapis.com"
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        oAuth2Repository.loadTokens()
                            .getOrNull()?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                    }
                    refreshTokens {
                        oAuth2Repository.refreshToken()
                            .getOrNull()?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                    }
                }
            }
        }
    }

    single(named(FIREBASE_CLIENT)) {
        val oAuth2Repository = get<OAuth2Repository>()

        HttpClient(OkHttp) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                url.protocol = URLProtocol.HTTPS
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                host = "firebase.googleapis.com"
            }

            install(Auth) {
                bearer {
                    loadTokens {
                        oAuth2Repository.loadTokens()
                            .getOrNull()?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                    }
                    refreshTokens {
                        oAuth2Repository.refreshToken()
                            .getOrNull()?.let {
                                BearerTokens(
                                    accessToken = it.accessToken,
                                    refreshToken = it.refreshToken
                                )
                            }
                    }
                }
            }
        }
    }

    single(named(GOOGLE_OAUTH2_CLIENT)) {
        HttpClient(OkHttp) {
            expectSuccess = true
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                accept(ContentType.Application.Json)
                contentType(ContentType.Application.Json)
                url.protocol = URLProtocol.HTTPS
                host = "oauth2.googleapis.com"
            }
        }

    }

}
