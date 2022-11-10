package com.stanislav.cryptochart.di

import android.util.Log
import com.stanislav.cryptochart.data.remote.api.CoinMarketCapApi
import com.stanislav.cryptochart.utils.Constants.BASE_URL
import com.stanislav.cryptochart.utils.Constants.CLIENT_TIME_OUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message -> Log.d("OkHttp: ", message) }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(httpLoggingInterceptor)
        connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
        retryOnConnectionFailure(true)
    }.build()

    @Singleton
    @Provides
    fun provideCatsApiService(
        okHttpClient: OkHttpClient
    ): CoinMarketCapApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CoinMarketCapApi::class.java)
    }
}