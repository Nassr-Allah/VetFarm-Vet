package com.nassrallah.vetfarmvet.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nassrallah.vetfarmvet.feature_auth.data.data_source.AuthDataSource
import com.nassrallah.vetfarmvet.feature_auth.data.data_source.local.AppDataStore
import com.nassrallah.vetfarmvet.feature_auth.data.repository.AuthRepositoryImpl
import com.nassrallah.vetfarmvet.feature_auth.domain.repository.AuthRepository
import com.nassrallah.vetfarmvet.feature_auth.domain.use_case.CreateVetUseCase
import com.nassrallah.vetfarmvet.feature_auth.domain.use_case.LoginVetUseCase
import com.nassrallah.vetfarmvet.feature_requests.data.data_source.remote.VetRequestDataSource
import com.nassrallah.vetfarmvet.feature_requests.data.repository.VetRequestRepositoryImpl
import com.nassrallah.vetfarmvet.feature_requests.domain.repository.VetRequestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "app_data")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient {
            install(Auth) {
                bearer {  }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }
    }

    @Provides
    @Singleton
    fun provideAppDataStore(@ApplicationContext context: Context) = AppDataStore(context.datastore)

    @Provides
    @Singleton
    fun provideVetRequestRepository(client: HttpClient): VetRequestRepository {
        return VetRequestRepositoryImpl(VetRequestDataSource(client))
    }

    @Provides
    @Singleton
    fun provideAuthRepository(client: HttpClient, dataStore: AppDataStore): AuthRepository {
        return AuthRepositoryImpl(AuthDataSource(client), dataStore)
    }

    @Provides
    @Singleton
    fun provideCreateVetUseCase(repository: AuthRepository) = CreateVetUseCase(repository)

    @Provides
    @Singleton
    fun provideLoginVetUseCase(repository: AuthRepository) = LoginVetUseCase(repository)

}