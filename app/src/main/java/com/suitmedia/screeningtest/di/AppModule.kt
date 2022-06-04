package com.suitmedia.screeningtest.di

import android.app.Application
import com.google.gson.GsonBuilder
import com.suitmedia.screeningtest.BuildConfig
import com.suitmedia.screeningtest.api.AppService
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    /* --------------------------------- Provide Remote Data Source ------------------------------*/
    @Singleton
    @Provides
    fun provideSurveyService(@AppAPI okhttpClient: OkHttpClient,
                             converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, AppService::class.java)

    /*@Singleton
    @Provides
    fun provideGoogleService(okhttpClient: OkHttpClient, converterFactory: GsonConverterFactory) = provideGoogleService(okhttpClient, converterFactory, GoogleService::class.java)*/

    /*@Singleton
    @Provides
    fun provideRemoteDataSource(surveyService: AppService) = MasterSurveyRemoteDataSource(surveyService)*/
    //fun provideRemoteDataSource(surveyService: AppService, googleService: GoogleService) = MasterSurveyRemoteDataSource(surveyService, googleService)

    @AppAPI
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            //.addInterceptor(AuthInterceptor(BuildConfig.API_DEVELOPER_TOKEN))
            .build()
    }

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
                                   converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    @CoroutineScopeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)

}