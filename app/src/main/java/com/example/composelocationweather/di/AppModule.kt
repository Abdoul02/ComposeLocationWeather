package com.example.composelocationweather.di

import android.content.Context
import androidx.room.Room
import com.example.composelocationweather.feature_weather.data.local.LocationWeatherDB
import com.example.composelocationweather.feature_weather.data.local.dao.CurrentWeatherDao
import com.example.composelocationweather.feature_weather.data.local.dao.ForecastDataDao
import com.example.composelocationweather.feature_weather.data.remote.RetrofitAPI
import com.example.composelocationweather.feature_weather.data.repository.WeatherRepositoryImpl
import com.example.composelocationweather.feature_weather.domain.repository.WeatherRepository
import com.example.composelocationweather.feature_weather.domain.use_case.GetCurrentWeather
import com.example.composelocationweather.feature_weather.domain.use_case.GetForecastData
import com.example.composelocationweather.feature_weather.domain.use_case.WeatherUseCase
import com.example.composelocationweather.util.ConstantData.BASE_URL
import com.example.composelocationweather.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(context, LocationWeatherDB::class.java, "LocationWeatherDB").build()

    @Singleton
    @Provides
    fun injectCurrentWeatherDao(
        database: LocationWeatherDB,
    ) = database.currentWeatherDao()

    @Singleton
    @Provides
    fun injectForecastDataDao(
        database: LocationWeatherDB,
    ) = database.forecastDataDao()

    @Singleton
    @Provides
    fun provideWeatherUseCase(repository: WeatherRepository, utils: Utils): WeatherUseCase {
        return WeatherUseCase(
            getCurrentWeather = GetCurrentWeather(repository, utils),
            getForecastData = GetForecastData(repository, utils)
        )
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        currentWeatherDao: CurrentWeatherDao,
        forecastDataDao: ForecastDataDao,
        retrofitApi: RetrofitAPI
    ) = WeatherRepositoryImpl(currentWeatherDao, forecastDataDao, retrofitApi) as WeatherRepository

    @Singleton
    @Provides
    fun injectRetrofitAPI(retrofit: Retrofit): RetrofitAPI {
        return retrofit.create(RetrofitAPI::class.java)
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpCache(@ApplicationContext context: Context): Cache {
        val cache = 10 * 1024 * 1024
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cache.toLong())
    }

    @Provides
    @Singleton
    fun getOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .cache(cache)
            .build()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }
}